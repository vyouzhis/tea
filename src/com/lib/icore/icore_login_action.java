package com.lib.icore;

import org.ppl.BaseClass.BaseiCore;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;
import org.ppl.io.TimeClass;

public class icore_login_action extends BaseiCore {
	private String className = null;

	public icore_login_action() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		TimeClass tc = TimeClass.getInstance();
		UrlClassList ucl = UrlClassList.getInstance();
			
		echo(SessAct.GetSession(globale_config.KaptchSes));
		
		if (super.Init() == 0) {
			String ok_url = ucl.BuildUrl("icore", tc.time() + "");
			
			TipMessage(ok_url, _CLang("ok_welcome"));
		} else {
			String err_url = ucl.BuildUrl("login", "");
			
			TipMessage(err_url, _CLang("error_passwd_or_name"));
		}
	}
}
