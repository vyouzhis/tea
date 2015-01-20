package org.ppl.BaseClass;

import org.ppl.core.PObject;
import org.ppl.etc.Config;

public class LibLang extends PObject implements BaseLangInterface {
	public Config CLang = null;
	private String selfPath = "";
	public LibLang() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String _Lang(String key) {
		// TODO Auto-generated method stub
		LangConfig();
		return CLang.GetValue(key);
	}
	
	public void LangConfig() {
		String LibName = SliceName(stdClass);
		String path = "properties/lang/zh_cn"+selfPath+"/"+LibName.substring(1)+".properties";
		//echo(path);
		CLang = new Config(path);	
	}
	
	public void SelfPath(String path) {
		String[] p = path.split("\\.");
		for (int i = 2; i < p.length; i++) {
			selfPath += "/"+p[i] ;	
		}
		
	}
}
