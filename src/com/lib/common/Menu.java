package com.lib.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseModule;
import org.ppl.etc.UrlClassList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Menu extends BaseModule {
	private String className = null;
	private JSONObject RoleJson;

	public Menu() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
	}

	@Override
	public void filter() {
		// TODO Auto-generated method stub
		myRole();
		setRoot("fun", this);
		super.View();
	}

	private void myRole() {
		String role = aclfetchMyRole();
		
		if(role == null)return;
		RoleJson = JSON.parseObject(role);
		if(RoleJson == null)return ;
		
		setRoot("myrole", JsonToMap(role));
	}

	@SuppressWarnings("unchecked")
	public boolean isMainMenuActive(String main) {
		List<String> rmc = porg.getRmc();
		
		Object libs = RoleJson.get(main);
		
		Map<String, Object> libO = JSON.parseObject(libs.toString(), Map.class);
		
		return libO.containsKey(rmc.get(0));
	}
	
	public boolean isLib(String lib) {
		List<String> rmc = porg.getRmc();
		return rmc.get(0).equals(lib);
	}
	
	private Map<String,Map<String, String>> JsonToMap(String json) {
		JSONObject subJson = JSON.parseObject(json);
		Map<String,Map<String, String>> res = new HashMap<>();
		
		Map<String, String> subMap ;
		for (String key : subJson.keySet()) {
			String dbJson = subJson.getString(key);
			JSONObject dbJ = JSON.parseObject(dbJson);
			subMap = new HashMap<String, String>();
			for (String k : dbJ.keySet()) {				
				subMap.put(k, dbJ.getString(k));
			}
			res.put(key, subMap);
		}
		
		return res;
	}
	
	public String MenuUrl(String lib) {
		UrlClassList ucl = UrlClassList.getInstance();
		return ucl.read(lib);
	}
	
	public String libName(String lib) {
		
		setStdName(lib);
		return _Lang("name");
	}

}
