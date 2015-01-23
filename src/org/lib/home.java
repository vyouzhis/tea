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

		setRoot("fun", this);

		super.View();
	}

	public String add(String s) {
		return s + " ok !";
	}

	public int mul(int a, int b) {
		return a * b;
	}
}
