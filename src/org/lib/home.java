package org.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseTheme;
import org.ppl.common.PorG;

public class home extends BaseTheme {
	private String className = null;
	
	public home() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}
	
	@Override
	public void Show() {
		// TODO Auto-generated method stub
		
		PorG pG = PorG.getInstance();
		String id = pG.porg("id");
				
		Map<String, Object> root = new HashMap<String, Object>();
	
		root.put("user", "Big Joe");
	
		Map<String, Object> latest = new HashMap<String, Object>();
	
		root.put("latestProduct", latest);
	
		latest.put("url", "products/greenmouse.html");
		latest.put("name", "green mouse");
	
		List<String> countries = new ArrayList<String>();
		if(id!=null){
			countries.add("India:"+id);
		}
		countries.add("United States");
		countries.add("Germany");
		countries.add("France");

		root.put("countries", countries);
		
		root.put("fun", this);

		super.View(root);
	}

	public String add(String s) {
		return s + " ok !";
	}

	public int mul(int a, int b) {
		return a * b;
	}

	@Override
	public void UrlServlet(List<String> arg) {
		// TODO Auto-generated method stub
		
	}

}
