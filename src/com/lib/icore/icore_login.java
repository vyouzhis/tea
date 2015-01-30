package com.lib.icore;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.etc.UrlClassList;
import org.ppl.io.Encrypt;
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
		UrlClassList ucl = UrlClassList.getInstance();
		TimeClass tc = TimeClass.getInstance();
		
		String salt = getSalt();
		setRoot("icore_url", ucl.BuildUrl("icore", tc.time()+""));
		setRoot("salt", salt);
				
		SurfaceFooter footer = new SurfaceFooter();	
		footer.setRoot("register_js", "1");
		footer.filter();
		footer_html = footer.getHtml();
		
		super.View();
	}
}
