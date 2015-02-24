package org.ppl.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ppl.BaseClass.BasePrograma;
import org.ppl.BaseClass.Permission;
import org.ppl.Module.ModuleBind;
import org.ppl.etc.Config;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;
import org.ppl.io.TimeClass;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class ACLControl extends ACLRole {

	public void aclLoadLib() {
		Injector injector = Guice.createInjector(new ModuleBind());
		UrlClassList ucl = UrlClassList.getInstance();
		Map<String, List<String>> PackClassList;
		PackClassList = ucl.getPackClassList();
		// HashMap<String, HashMap> selects = new HashMap<String, HashMap>();

		for (Entry<String, List<String>> entry : PackClassList.entrySet()) {
			// String key = entry.getKey();

			List<String> value = entry.getValue();
			for (int i = 0; i < value.size(); i++) {
				if (value.get(i).substring(value.get(i).length() - 6)
						.equals("_index")) {
					BasePrograma Index = (BasePrograma) injector
							.getInstance(Key.get(BasePrograma.class,
									Names.named(value.get(i))));
					Index._MLang("name");
				} else {
					Permission home = (Permission) injector.getInstance(Key
							.get(Permission.class, Names.named(value.get(i))));
					home._MLang("name");
					home._MLang("desc");
				}
			}
		}
	}

	public String IndexName(String value) {

		if (value.matches("(.*)_index")) {
			Injector injector = Guice.createInjector(new ModuleBind());
			BasePrograma Index = (BasePrograma) injector.getInstance(Key.get(
					BasePrograma.class, Names.named(value)));
			return Index._CLang(value);
		}
		return "not has lan key";
	}

	public Map<String, String> LibInfo(String value) {
		Injector injector = Guice.createInjector(new ModuleBind());
		Map<String, String> info = new HashMap<String, String>();

		Permission home = (Permission) injector.getInstance(Key.get(
				Permission.class, Names.named(value)));
		info.put("name", home._MLang("name"));
		info.put("desc", home._MLang("desc"));
		return info;
	}

	public boolean CheckOntime() {
						
		String time = SessAct.GetSession(mConfig.GetValue(globale_config.Ontime));
		if(time==null)return false;
		
		int ontime = Integer.valueOf(time);
		TimeClass tc = TimeClass.getInstance();
		int now = (int) tc.time();
		int timeOut = mConfig.GetInt(globale_config.TimeOut);
		if(now-ontime>timeOut){	
			List<String> rmc = porg.getRmc();
			if (rmc.size() < 2){
				aclLogout();
				return true;
			}else{
				return false;
			}
		}
		
		SessAct.SetSession(mConfig.GetValue(globale_config.Ontime), now+"");
		
		return true;
	}

}
