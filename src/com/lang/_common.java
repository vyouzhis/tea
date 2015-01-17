package com.lang;

import org.ppl.BaseClass.LibLang;
import org.ppl.etc.Config;

public class _common extends LibLang {

	public _common() {
		// TODO Auto-generated constructor stub
		String className = this.getClass().getCanonicalName();
		
		String cName = SliceName(className);
		
		String path = "properties/lang/zh_cn/"+cName.substring(1)+".properties";		
		super.CLang = new Config(path);	
	}
}
