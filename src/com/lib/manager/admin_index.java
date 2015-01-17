package com.lib.manager;

import java.util.HashMap;
import java.util.Map;

import org.ppl.BaseClass.Permission;
import org.ppl.common.PorG;
import org.ppl.etc.UrlClassList;

import com.lib.common.Header;

public class admin_index extends Permission {

	public admin_index() {
		// TODO Auto-generated constructor stub
		String className = this.getClass().getCanonicalName();
		// stdClass = className;
		super.GetSubClassName(className);

	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub

		if (super.Init() == -1)
			return;
		Map<String, Object> root = new HashMap<String, Object>();
		PorG pg = PorG.getInstance();

		root.put("static_uri", pg.getContext_Path());

		String UserName = aclgetName();
		root.put("UserName", UserName);

		root.put("navbar", navbar());

		root.put("menu", menu());

		super.View(root);
	}

}
