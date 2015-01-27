package com.lib.icore;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.etc.UrlClassList;
import org.ppl.io.Encrypt;
import org.ppl.io.TimeClass;

public class icore_login extends BaseSurface {
	private String className = null;

	public icore_login() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		UrlClassList ucl = UrlClassList.getInstance();
		TimeClass tc = TimeClass.getInstance();
		Encrypt en = Encrypt.getInstance();
		String ok_action = en.MD5(String.valueOf(tc.time()));
		setRoot("icore_url", ucl.BuildUrl("icore", ok_action));
		super.View();
	}
}
