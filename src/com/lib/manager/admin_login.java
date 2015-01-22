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
		PorG pg = PorG.getInstance();
		UrlClassList ucl = UrlClassList.getInstance();
		
		setRoot("static_uri", pg.getContext_Path());
		setRoot("admin_login_action_uri", ucl.BuildUrl("admin_login_action", ""));
		setRoot("salt", getSalt());
		super.View();
	}
}
