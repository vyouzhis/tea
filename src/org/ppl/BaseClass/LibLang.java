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
				
		return CLang.GetValue(key);
	}
	
}
