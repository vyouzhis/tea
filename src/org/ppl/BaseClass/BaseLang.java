package org.ppl.BaseClass;

import org.ppl.Module.ModuleBind;
import org.ppl.core.ACLInit;
import org.ppl.core.PObject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public abstract class BaseLang extends ACLInit implements BaseLangInterface {
	private String StdName;
	
	@Override
	public String _Lang(String key) {
		// TODO Auto-generated method stub
		StdName = "_"+SliceName(stdClass);
		
		if(StdName.equals("_common")){
			return _CLang(key);
		}
		
		String val = _MLang(key);
		if(val==null){
			return _CLang(key);
		}
		
		return val;
	}

	public String _CLang(String key) {
		String stdName = "_common";
		
		return find(key, stdName);
	}
	
	public String _MLang(String key) {
		
		StdName = "_"+SliceName(stdClass);
				
		return find(key, StdName);
	}
	
	private String find(String key, String stdName) {
		//System.out.println("name:"+stdName+" key:"+key);
		Injector injector = Guice.createInjector(new ModuleBind());
		LibLang libLan = (LibLang) injector.getInstance(Key.get(
				LibLang.class, Names.named(stdName)));
		
		return libLan._Lang(key);
	}
	
	
}