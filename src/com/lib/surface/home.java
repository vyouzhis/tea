package com.lib.surface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.io.TimeClass;

public class home extends BaseSurface {
	private String className = null;

	public home() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
		isAutoHtml = false;
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		
		String id = porg.getKey("id");

		setRoot("user", "Big Joe");

		Map<String, Object> latest = new HashMap<String, Object>();

		setRoot("latestProduct", latest);

		latest.put("url", "products/greenmouse.html");
		latest.put("name", "green mouse");

		List<String> countries = new ArrayList<String>();
		if (id != null) {
			countries.add("India:" + id);
		}
		countries.add("United States");
		countries.add("Germany");
		countries.add("France");

		setRoot("countries", countries);
		TimeClass tClass = TimeClass.getInstance();
		
		setRoot("fun", this);
		List<String> msg = new ArrayList<>();
		
		msg.add("----msg time:"+tClass.TimeFormat("yyyy-MM-dd HH:mm:ss"));
		// thread Daemon
		TellPostMan("testThread", msg);
		
		List<String> msg2 = new ArrayList<>();
	
		msg2.add("====msg2 time:"+tClass.TimeFormat("yyyy-MM-dd HH:mm:ss"));
		TellPostMan("testThread", msg2);
		
		//super.View();
	}

	public String add(String s) {
		return s + " ok !";
	}

	public int mul(int a, int b) {
		return a * b;
	}
}
