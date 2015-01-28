package com.lib.icore;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;
import org.ppl.io.Encrypt;
import org.ppl.io.TimeClass;

import com.lib.common.SurfaceFooter;

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
		TimeClass tc = TimeClass.getInstance();
		Encrypt en = Encrypt.getInstance();
		String ok_action = en.MD5(String.valueOf(tc.time()));
		cookieAct.SetCookie(globale_config.CookieSalt, ok_action);
		
		setRoot("register_ok", ucl.BuildUrl("register_ok", ok_action));
		
		SurfaceFooter footer = new SurfaceFooter();	
		footer.setRoot("register_js", "1");
		footer.filter();
		footer_html = footer.getHtml();
		super.View();
	}
}
