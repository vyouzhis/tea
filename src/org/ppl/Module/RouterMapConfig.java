package org.ppl.Module;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ppl.BaseClass.BaseTheme;
import org.ppl.BaseClass.Permission;
import org.ppl.db.HikariConnectionPool;
import org.ppl.etc.Config;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class RouterMapConfig {
	List<String> RMC = null;
	private String servletPath = null;
	private String mehtod = null;
	private boolean NullMap = true;
	//private BaseTheme home = null;
	//private Config mConfig = null;
	private String htmlCon;
	private boolean isAjax = false;
	private UrlClassList ucl;
	private String BaseName = null;

	public RouterMapConfig() {
		// TODO Auto-generated constructor stub

		RMC = new ArrayList<String>();
		
		//mConfig = new Config(globale_config.UrlMap);
		ParserMap();
	}

	public List<String> getRMC() {
		return RMC;
	}

	public void map(String key) {
		servletPath = key.trim();
	}

	public void match() {
		if (matchRouter()) {
			if (BaseName != null && RMC.size() > 0) {				
				HikariConnectionPool hcp = new HikariConnectionPool();	
				Injector injector = Guice.createInjector(new ModuleBind());
				//System.out.println("match BaseName: " + BaseName);
				if(BaseName.length()>11 && BaseName.substring(0, 11).equals("Permission_")){
					BaseName = BaseName.substring(11);
					Permission home = (Permission) injector.getInstance(Key.get(Permission.class, Names.named(BaseName)));
					home.SetCon(hcp.GetCon(0));
					home.UrlServlet(RMC);
					home.Show();
					htmlCon = home.getHtml();
					//System.out.println(htmlCon);
					isAjax = home.isAjax();
				}else{
					BaseTheme home = (BaseTheme) injector.getInstance(Key.get(
						BaseTheme.class, Names.named(BaseName)));
					home.SetCon(hcp.GetCon(0));
					home.UrlServlet(RMC);
					home.Show();
					htmlCon = home.getHtml();
					isAjax = home.isAjax();
				}
								
				NullMap = false;
			}
		}
	}

	public boolean routing() {
		return NullMap;
	}

	public String getHtml() {
		return htmlCon;
	}

	public String setContentType() {
		if (isAjax) {
			return "application/json";
		} else {
			return "text/html";
		}
	}

	private boolean matchRouter() {

		if (servletPath.length() > 0 && servletPath.substring(0, 1).equals("/")) {
			servletPath = servletPath.substring(1);
		}
		//System.out.println("servletpath: "+servletPath);
		String[] UrlServlet = servletPath.split("/");
		String uri;
		String mMthod = null;

		List<String> lu = ucl.getUcls();
		
		// System.out.println("getUcls: " + lu.size());
		for (int i = 0; i < lu.size(); i++) {
			RMC.clear();
			BaseName = lu.get(i).toString();
						
			mMthod = GetMapMethod(BaseName);
			if(mMthod == null)continue;
			
			int l = mMthod.toLowerCase()
					.indexOf(mehtod.toLowerCase());
			if (l == -1) {
				BaseName = null;
				//System.out.println("method is not");
				continue;
			}
			
			uri = GetMapUri(BaseName);
			//System.out.println(uri);
			if (uri.length() > 0 && uri.substring(0, 1).equals("/")) {
				uri = uri.substring(1);
			}
			
			String[] uris = uri.split("/");

			if (uri.length() == 0 && servletPath.length() == 0){				
				//System.out.println("uri equals index " + uri);
				RMC.add("");
				return true;
			}else if (uri.length() == 0 && servletPath.length() != 0) {
				continue;
			}
			
			if (uri.length()>1 && uri.substring(uri.length()-1).equals("?")) {
				if (uris.length < UrlServlet.length) {
					BaseName = null;
					//System.out.println("UrlServlet.length < "
					//		+ UrlServlet.length);
					continue;
				}
			} else {

				if (uris.length != UrlServlet.length) {
					BaseName = null;
					//System.out.println("UrlServlet.length != "
					//		+ UrlServlet.length);
					continue;
				}
			}
			
			for (int j = 0; j < UrlServlet.length; j++) {
				//System.out.println("uri: "+uris[j]+" us:"+UrlServlet[j]);
				Pattern r = Pattern.compile(uris[j]);
				if(uris[j].length()>11 && uris[j].substring(0, 11).equals("Permission_")){
					r = Pattern.compile(uris[j].substring(11));
				}				
				//
				Matcher m = r.matcher(UrlServlet[j]);
				if (m.find()) {
					RMC.add(m.group());
//					System.out.println("Found value: " + BaseName + " arg:"
//							+ m.group());
				} else {
					RMC.clear();
					break;
				}
			}

			if (RMC.size() == UrlServlet.length)
				return true;

			BaseName = null;
		}

		return false;
	}

	public String getMehtod() {
		return mehtod;
	}

	public void setMehtod(String mehtod) {
		this.mehtod = mehtod;
	}

	private void ParserMap() {
		String[] uri = null;
		Config mConfig = new Config(globale_config.UrlMap);
		ucl = UrlClassList.getInstance();

		if (ucl.getUcls() == null) {
			for (Object um : mConfig.getKey()) {
				uri = um.toString().split("\\.");
				if (uri.length > 1 && uri[1].equals("uri")) {
					ucl.setUcls(uri[0]);
				}
			}
		}
				
		List<String> pum = PermUrlMap();
		for (int i = 0; i < pum.size(); i++) {
			ucl.setUcls(pum.get(i));
		}
		
	}
	
	private List<String> PermFileList(String directoryName) {
		List<String> fl = new ArrayList<String>();	    
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();

	    for (File file : fList) {
	        if (file.isFile()) {
	        	fl.add("Permission_"+file.getName().split("\\.")[0]);	     
	        } else if (file.isDirectory()) {
	            fl.addAll(PermFileList(file.getAbsolutePath()));
	        }
	    }	    
	    return fl;
	}
	
	private List<String> PermUrlMap() {
		Config mConfig = new Config(globale_config.Config);
		String path = this.getClass().getResource("/").getPath()+mConfig.GetValue("perm_class_path");				
		
		List<String> pum = PermFileList(path);
		
		return pum;			
	}
	
	private String GetMapMethod(String key) {
		Config mConfig = new Config(globale_config.UrlMap);
		String method = mConfig.GetValue(key + ".method");
		if(method==null) {
			List<String> pum = PermUrlMap();
			if (pum.contains(key)) {
				return "POST|GET";
			}
		}
		
		return method;
	}
	
	private String GetMapUri(String key) {
		Config mConfig = new Config(globale_config.UrlMap);
		String uri = mConfig.GetValue(key + ".uri");
		if(uri==null) {
			List<String> pum = PermUrlMap();
			if(pum.contains(key)){
				return key+"/(read|create|edit|remove|search)";
			}
		}
		
		return uri;
	}
}
