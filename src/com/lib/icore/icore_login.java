package com.lib.icore;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;
import org.ppl.io.TimeClass;

import com.lib.common.SurfaceFooter;

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
		TimeClass tc = TimeClass.getInstance();
		UrlClassList ucl = UrlClassList.getInstance();
		if (isLogin() > 0) {
			
			ShowMessage ms = ShowMessage.getInstance();
			String url = ucl.BuildUrl("icore", tc.time() + "");
			
			ms.ShowMsg(url);

			return;
		}

		String salt = getSalt();
		setRoot("icore_url", ucl.BuildUrl("icore_login_action", tc.time() + ""));
		setRoot("salt", salt);

		SurfaceFooter footer = new SurfaceFooter();
		footer.setRoot("register_js", "1");
		footer.filter();
		footer_html = footer.getHtml();

		super.View();
	}
}
