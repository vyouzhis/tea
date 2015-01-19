package org.ppl.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BasePrograma;
import org.ppl.BaseClass.BaseTheme;
import org.ppl.BaseClass.Permission;
import org.ppl.Module.ModuleBind;

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
		//List<Map<String, Map<String, String>>> lib_array = new ArrayList<Map<String, Map<String, String>>>();
		List<Map<String, String>> lib_array = new ArrayList<Map<String,String>>();  
		Injector injector = Guice.createInjector(new ModuleBind());
		List<String> pum = PermUrlMap();
		String libName = "";
		String name = "";
		String desc = "";
		for (int i = 0; i < pum.size(); i++) {
			libName = pum.get(i);
			
			if (libName.substring(libName.length() - 6, 6).equals("_index")) {
				BasePrograma home = (BasePrograma) injector.getInstance(Key
						.get(BasePrograma.class, Names.named(libName)));
				name = home.getProg();
				Map<String, String> Index = new HashMap<String, String>();
				Index.put("name", name);
				lib_array.add(Index);
			} else if (libName.length()>11) {			
				libName = libName.substring(11);
				Permission home = (Permission) injector.getInstance(Key.get(
						Permission.class, Names.named(libName)));
				name = home._MLang("name");
				desc = home._MLang("desc");
			}
		}

	}
}
