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
				
		super.View(Data());
	}

	private Map<String, Object> Data() {
		
		Map<String, Object> root;
		root = new HashMap<String, Object>();

		root.put("about", "about this com.");

		PorG pG = PorG.getInstance();
		String fname = pG.porg("fname[0]");
		String fname1 = pG.porg("fname[1]");
		String fn = pG.porg("fn");
		if (fname != null) {
			root.put("post_data", "About Show:" + fname);
			root.put("post_data1", "About Show1:" + fname1);

		}
		if (fn != null)
			root.put("fn", "get fn:" + fn);
		// pG.getFile();
		String name = aclgetNickName();
		
		root.put("tdesc", _MLang("desc"));
		root.put("tlang", _Lang("tl"));
		root.put("welcome", _CLang("welcome"));
		root.put("wels", name);
		
		return root;
	}

	@Override
	public void UrlServlet(List<String> arg) {
		// TODO Auto-generated method stub
		
	}
	

}
