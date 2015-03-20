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
	
	/**
	 * @since echo
	 * @param o
	 */
	public void echo(Object o) {
		System.out.println(o);
	}
	
	/**
	 * @GetSubClassName tell SubClassName to mainClass
	 * @param subClassname
	 */
	protected void GetSubClassName(String subClassname) {
		stdClass = subClassname;
	}

	/**
	 * @since slice pack name for . 
	 * @param k
	 * @return
	 */
	public String SliceName(String k) {
		String[] name = k.split("\\.");
		String cName = name[name.length - 1];
		return cName;
	}

	/**
	 * @since get bind name
	 * @return
	 */
	public String getBindName() {
		if (BindName == null) {
			BindName = stdClass;

		}
		return BindName;
	}

	/**
	 * @since set bind name
	 * @param bindName
	 */
	public void setBindName(String bindName) {
		BindName = bindName;
	}

	/**
	 * @since create one salt encry
	 * @return String
	 */
	public String getSalt() {
		TimeClass tc = TimeClass.getInstance();
		Encrypt ec = Encrypt.getInstance();

		String salt = ec.MD5(String.valueOf(tc.time()));
		Config mConfig = new Config(globale_config.Config);

		SessAct.SetSession(mConfig.GetValue(globale_config.SessSalt), salt);
				
		return salt;
	}
	
	/**
	 * @since check the last salt
	 * @param salt
	 * @return boolean
	 */
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

	/**
	 * @since find pack list 
	 * @param directoryName
	 * @return List<String>
	 */
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

	/**
	 * @since fine url map
	 * @return 
	 */
	public List<String> PermUrlMap() {
		Config mConfig = new Config(globale_config.Config);
		String path = this.getClass().getResource("/").getPath()
				+ mConfig.GetValue("perm_class_path");

		List<String> pum = PermFileList(path);

		return pum;
	}
	
	/**
	 * @since find class pack
	 * @param path
	 */
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
	
	/**
	 * @since tell post man do something
	 * @param ThreadName
	 * @param message
	 */
	public void TellPostMan(String ThreadName, Object message) {

		synchronized (globale_config.RapidListQueue) {
			if (globale_config.RapidListQueue.containsKey(ThreadName)) {
				globale_config.RapidListQueue.get(ThreadName).add(message);
				echo("tellpostman exists");
			} else {
				LinkedList<Object> m = new LinkedList<Object>();
				m.add(message);
				globale_config.RapidListQueue.put(ThreadName, m);
				echo(" tellpostman new ");
			}
			// message.setText("Notifier took a nap for 3 seconds");
			// System.out.println("Notifier is notifying waiting thread to wake up at "
			// + new Date());
			globale_config.RapidListQueue.notify();
		}
	}
	
	/**
	 * @since time
	 * @return
	 */
	public int time() {
		TimeClass tc = TimeClass.getInstance();
		return (int)tc.time();
	}
	
	/**
	 * @since thread id
	 * @return
	 */
	public long myThreadId() {
		return Thread.currentThread().getId();
	}
	
	/**
	 * @since check email
	 * @param emailAddress
	 * @return
	 */
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
