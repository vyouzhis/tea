package com.lib.surface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.BaseClass.BaseTheme;
import org.ppl.common.PorG;
import org.ppl.io.TimeClass;

public class About extends BaseSurface{
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
		
		
		setRoot("about", "about this com.");
		
		
		String fname = porg.getKey("fname[0]");
		String fname1 = porg.getKey("fname[1]");
		String fn = getSalt();
		if(fname!=null ){
			setRoot("post_data", "About Show:"+fname);
			setRoot("post_data1", "About Show1:"+fname1);
			int i = aclLogin(fname, fname1, fn);
			echo("login: "+i);
		}
		if(fn!=null)
			setRoot("fn", "get fn:"+fn);
		//pG.getFile();
		
		TimeClass tClass = TimeClass.getInstance();
		
		
		Map<String, String> msg = new HashMap<>();
		
		
		msg.put("+++time","msg time:"+tClass.TimeFormat("yyyy-MM-dd HH:mm:ss"));
		TellPostMan("rapi_two", msg);
		
		super.View();
	}

}
