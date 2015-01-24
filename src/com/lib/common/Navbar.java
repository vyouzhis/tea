package com.lib.common;

import org.ppl.BaseClass.BaseModule;
import org.ppl.common.PorG;
import org.ppl.etc.UrlClassList;

public class Navbar extends BaseModule {
	private String className = null;

	public Navbar() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
	}

	@Override
	public void filter() {
		// TODO Auto-generated method stub
		Data();
		super.View();
	}

	private void Data() {
		UrlClassList ucl = UrlClassList.getInstance();
		setRoot("UserName", aclGetNickName());
		setRoot("logout_url", ucl.BuildUrl("admin_login_action", ""));
		setRoot("my_profile", ucl.BuildUrl("my_profile", ""));
	}

}
