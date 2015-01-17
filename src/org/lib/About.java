package org.lib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseTheme;
import org.ppl.common.PorG;

public class About extends BaseTheme{
	private String className = null;
	
	public About() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
		isAutoHtml = false;
	}

	
	@Override
	public void Show() {
		// TODO Auto-generated method stub
		Map<String, Object> root = new HashMap<String, Object>();
		
		root.put("about", "about this com.");
		
		PorG pG = PorG.getInstance();
		String fname = pG.porg("fname[0]");
		String fname1 = pG.porg("fname[1]");
		String fn = getSalt();
		if(fname!=null ){
			root.put("post_data", "About Show:"+fname);
			root.put("post_data1", "About Show1:"+fname1);
			int i = aclLogin(fname, fname1, fn);
			echo("login: "+i);
		}
		if(fn!=null)
			root.put("fn", "get fn:"+fn);
		//pG.getFile();
		
		super.View(root);
	}


	@Override
	public void UrlServlet(List<String> arg) {
		// TODO Auto-generated method stub
		
	}

}
