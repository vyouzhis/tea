package com.lib.manager;

import java.util.HashMap;
import java.util.Map;

import org.ppl.BaseClass.Permission;
import org.ppl.common.PorG;
import org.ppl.etc.UrlClassList;

public class admin_login extends Permission {

	public admin_login() {
		// TODO Auto-generated constructor stub
		String className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}
	
	@Override
	public void Show() {
		// TODO Auto-generated method stub
		Map<String, Object> root = new HashMap<String, Object>();
		PorG pg = PorG.getInstance();
		UrlClassList ucl = UrlClassList.getInstance();
		
		root.put("static_uri", pg.getContext_Path());
		root.put("admin_login_action_uri", ucl.BuildUrl("admin_login_action", ""));
		root.put("salt", getSalt());
		super.View(root);
	}
}
