package org.ppl.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ppl.BaseClass.BasePrograma;
import org.ppl.BaseClass.BaseTheme;
import org.ppl.BaseClass.Permission;
import org.ppl.Module.ModuleBind;
import org.ppl.etc.UrlClassList;

import com.alibaba.fastjson.JSON;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class ACLControl extends ACLRole {

	private void aclfetchRole() {
		int uid = aclGetUid();
		String format = "SELECT g.permissions FROM "
				+ mConfig.GetValue("db_pre_rule") + "user_info u, "
				+ mConfig.GetValue("db_pre_rule")
				+ "group g where u.gid =  g.id and u.uid=%d LIMIT 1";
		String sql = String.format(format, uid);

		Map<String, Object> res = FetchOne(sql);
		if (res != null) {
			String json = JSON.toJSONString(res);

			SessAct.SetSession("role", json);
		}
	}

	public void aclLoadLib() {
		//Map<String, List<String>> plInfo = new HashMap<String, List<String>>();

		Injector injector = Guice.createInjector(new ModuleBind());
		UrlClassList ucl = UrlClassList.getInstance();
		Map<String, List<String>> PackClassList;
		PackClassList = ucl.getPackClassList();
		// HashMap<String, HashMap> selects = new HashMap<String, HashMap>();

		for (Entry<String, List<String>> entry : PackClassList.entrySet()) {
			//String key = entry.getKey();

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
		
		Permission home = (Permission) injector.getInstance(Key
				.get(Permission.class, Names.named(value)));
		info.put("name", home._MLang("name"));
		info.put("desc", home._MLang("desc"));		
		return info;
	}
}
