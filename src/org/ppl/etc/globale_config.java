package org.ppl.etc;

public class globale_config {
	public static globale_config config = null;
	
	public static String dbCase = "PGSource";  // db source
	public static String PropertiesPath = "properties/";
	public static String ext = ".properties";
	public static String Config = PropertiesPath+"config"+ext;
	public static String Mongo = PropertiesPath+"mongo"+ext;
	public static String Mysql = PropertiesPath+"mysql"+ext;
	public static String UrlMap = PropertiesPath+"UrlMap"+ext;
	
	//session
	public static String SessSalt = "session.salt";
	public static String sessAcl = "session.acl";
	
	
	
	public static globale_config getInstance() {
		if (config == null) {
			config = new globale_config();
		}

		return config;
	}

}