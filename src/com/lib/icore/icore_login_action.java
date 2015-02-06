package com.lib.icore;

import org.ppl.BaseClass.BaseiCore;
import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;
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
		ShowMessage ms = ShowMessage.getInstance();
		isAutoHtml=false;
		
		if (super.Init() == 0) {
			String ok_url = ucl.BuildUrl("icore", tc.time() + "");
			String res = ms.SetMsg(ok_url, _CLang("ok_welcome"), 3000);
			super.setHtml(res);
		} else {
			String err_url = ucl.BuildUrl("login", "");
			String res = ms.SetMsg(err_url, _CLang("error_passwd_or_name"),
					3000);
			super.setHtml(res);
		}
	}
}
