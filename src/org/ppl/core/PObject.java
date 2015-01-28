package org.ppl.core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.common.CookieAction;
import org.ppl.common.PorG;
import org.ppl.common.SessionAction;
import org.ppl.db.DBSQL;
import org.ppl.etc.Config;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;
import org.ppl.io.Encrypt;
import org.ppl.io.TimeClass;

public class PObject extends DBSQL {
	protected String stdClass = null;
	private String BindName = null;
	protected SessionAction SessAct = SessionAction.getInstance();
	protected CookieAction cookie = CookieAction.getInstance();
	protected PorG porg = PorG.getInstance();
	
	public void echo(Object o) {
		System.out.println(o);
	}

	protected void GetSubClassName(String subClassname) {
		stdClass = subClassname;
	}

	public String SliceName(String k) {
		String[] name = k.split("\\.");
		String cName = name[name.length - 1];
		return cName;
	}

	public String getBindName() {
		if (BindName == null) {
			BindName = stdClass;

		}
		return BindName;
	}

	public void setBindName(String bindName) {
		BindName = bindName;
	}

	public String getSalt() {
		TimeClass tc = TimeClass.getInstance();
		Encrypt ec = Encrypt.getInstance();

		String salt = ec.MD5(String.valueOf(tc.time()));
		Config mConfig = new Config(globale_config.Config);

		SessAct.SetSession(mConfig.GetValue(globale_config.SessSalt), salt);
		cookie.SetCookie(mConfig.GetValue(globale_config.CookieSalt), salt);
		
		return salt;
	}

	public String easyGetSalt() {
		Config mConfig = new Config(globale_config.Config);
		String salt = SessAct.GetSession(mConfig
				.GetValue(globale_config.SessSalt));
		
		
		return salt;
	}
	
	public boolean easyCheckSalt(String salt) {
		Config mConfig = new Config(globale_config.Config);

		String sess_salt = SessAct.GetSession(mConfig
				.GetValue(globale_config.SessSalt));
		
		if (sess_salt == null)
			return false;
		return sess_salt.equals(salt);		
	}
	
	public boolean checkSalt(String salt) {

		Config mConfig = new Config(globale_config.Config);
		Encrypt ec = Encrypt.getInstance();
		TimeClass tc = TimeClass.getInstance();
		String new_salt = ec.MD5(String.valueOf(tc.time()));

		String sess_salt = SessAct.GetSession(mConfig
				.GetValue(globale_config.SessSalt));
		
		if (sess_salt == null)
			return false;
		if (sess_salt.equals(salt)) {
			SessAct.SetSession(mConfig.GetValue(globale_config.SessSalt),
					new_salt);
			return true;
		}

		return false;
	}

	public List<String> PermFileList(String directoryName) {
		List<String> fl = new ArrayList<String>();
		File directory = new File(directoryName);
		Map<String, List<String>> PackClassList;
		UrlClassList ucl = UrlClassList.getInstance();
		PackClassList = ucl.getPackClassList();
		if(PackClassList==null){
			PackClassList = new HashMap<String, List<String>>();
		}
		// get all the files from a directory
		File[] fList = directory.listFiles();

		for (File file : fList) {
			if (file.isFile()) {
				// echo("name:"+directory.getName()+"__"+file.getName());
				String lib = file.getName().split("\\.")[0];
				String index = directory.getName();
				if (!index.equals("manager") && !lib.matches("(.*)_index")) {
					
					if (PackClassList.get(index) != null) {
						if (!PackClassList.get(index).contains(lib) )
							PackClassList.get(index).add(lib);
					} else {
						List<String> l = new ArrayList<String>();
						l.add(lib);
						PackClassList.put(index, l);
					}
				}
				fl.add("Permission." + lib);
			} else if (file.isDirectory()) {
				fl.addAll(PermFileList(file.getAbsolutePath()));
			}
		}
		ucl.setPackClassList(PackClassList);
		
		return fl;
	}

	public List<String> PermUrlMap() {
		Config mConfig = new Config(globale_config.Config);
		String path = this.getClass().getResource("/").getPath()
				+ mConfig.GetValue("perm_class_path");

		List<String> pum = PermFileList(path);

		return pum;
	}

}
