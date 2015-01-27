package com.lib.icore;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.etc.UrlClassList;

public class Register extends BaseSurface {
	private String className = null;

	public Register() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		UrlClassList ucl = UrlClassList.getInstance();
		setRoot("register_ok", ucl.BuildUrl("register_ok", ""));
		super.View();
	}
}
