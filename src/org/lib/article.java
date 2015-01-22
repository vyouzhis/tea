package org.lib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseTheme;
import org.ppl.common.PorG;

public class article extends BaseTheme {
	private String className = null;
	 
	public article() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
		isAutoHtml = false;
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		Data();
		super.View();
	}

	private void Data() {
					
		setRoot("about", "about this com.");

		
		String fname = porg.getKey("fname[0]");
		String fname1 = porg.getKey("fname[1]");
		String fn = porg.getKey("fn");
		if (fname != null) {
			setRoot("post_data", "About Show:" + fname);
			setRoot("post_data1", "About Show1:" + fname1);

		}
		if (fn != null)
			setRoot("fn", "get fn:" + fn);
		// pG.getFile();
		String name = aclgetNickName();
		
		setRoot("tdesc", _MLang("desc"));
		setRoot("tlang", _Lang("tl"));
		setRoot("welcome", _CLang("welcome"));
		setRoot("wels", name);
		
		
	}

	@Override
	public void UrlServlet(List<String> arg) {
		// TODO Auto-generated method stub
		
	}
	

}
