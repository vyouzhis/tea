package org.ppl.BaseClass;

import org.ppl.core.PObject;
import org.ppl.etc.Config;

public class LibLang extends PObject implements BaseLangInterface {
	public Config CLang = null;
	
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
		String path = "properties/lang/zh_cn/"+LibName.substring(1)+".properties";
		CLang = new Config(path);	
	}
	
}
