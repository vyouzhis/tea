package org.ppl.core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ppl.common.CookieAction;
import org.ppl.common.PorG;
import org.ppl.common.SessionAction;
import org.ppl.etc.Config;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;
import org.ppl.io.Encrypt;
import org.ppl.io.TimeClass;

public class PObject {
	protected String stdClass = null;
	private String BindName = null;
	protected Config myConfig = new Config(globale_config.Mysql);
	protected Config mConfig = new Config(globale_config.Config);
	protected Config uConfig = new Config(globale_config.UrlMap);
	protected Config mgConfig = new Config(globale_config.Mongo);
	protected Config mailConfig = new Config(globale_config.Mail);
	
	protected SessionAction SessAct = SessionAction.getInstance();
	protected CookieAction cookieAct = CookieAction.getInstance();
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
				
		return salt;
	}
	
	public boolean checkSalt(String salt) {

		Encrypt ec = Encrypt.getInstance();
		String new_salt = ec.MD5(String.valueOf(time()));

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
	
	public void findPack(String path) {
		UrlClassList ucl = UrlClassList.getInstance();
		File directory = new File(path);
		File[] fList = directory.listFiles();
		String[] pack = path.split("classes");
		if(pack.length != 2)return;
		String pn = pack[1].replace("/", ".");
		pn = pn.replace("\\", ".");
		
		if(!pn.substring(pn.length()-1, pn.length()).equals(".")){
			pn = pn+".";
		}
		pn = pn.substring(1);
		
		for (File file : fList) {
			if (file.isFile()) {
				String lib = file.getName().split("\\.")[0];				
				ucl.setPackList(pn+lib);				
			} else if (file.isDirectory()) {				
				findPack(file.getAbsolutePath());
			}
		}
	}
	
	public void TellPostMan(String ThreadName, Object message) {
		if(globale_config.RapidListQueue.containsKey(ThreadName)){
			globale_config.RapidListQueue.get(ThreadName).add(message);
		}else {
			LinkedList<Object> l = new LinkedList<Object>();
			l.add(message);
			globale_config.RapidListQueue.put(ThreadName, l);
		}
		synchronized (globale_config.RapidListQueue) {
			//message.setText("Notifier took a nap for 3 seconds");
			//System.out.println("Notifier is notifying waiting thread to wake up at " + new Date());
			globale_config.RapidListQueue.notify();
		}
	}
	
	public int time() {
		TimeClass tc = TimeClass.getInstance();
		return (int)tc.time();
	}
	
	public long myThreadId() {
		return Thread.currentThread().getId();
	}
	
	public  boolean validateEmailAddress(String emailAddress) {
		 Pattern regexPattern;
		 Matcher regMatcher;
	    regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
	    regMatcher   = regexPattern.matcher(emailAddress);
	    if(regMatcher.matches()){
	        return true;
	    } else {
	    return false;
	    }
	}
		
}
