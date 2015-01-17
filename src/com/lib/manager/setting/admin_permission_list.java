package com.lib.manager.setting;

import java.util.HashMap;
import java.util.Map;

import org.ppl.BaseClass.BasePerminterface;
import org.ppl.BaseClass.Permission;
import org.ppl.common.PorG;

public class admin_permission_list extends Permission implements
		BasePerminterface {

	public admin_permission_list() {
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

	@Override
	public void read(Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void create(Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void edit(Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void search(Object arg) {
		// TODO Auto-generated method stub

	}

}
