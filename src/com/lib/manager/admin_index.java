package com.lib.manager;

import org.ppl.BaseClass.Permission;
import org.ppl.common.PorG;


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
		
		PorG pg = PorG.getInstance();

		setRoot("static_uri", pg.getContext_Path());

		String UserName = aclgetName();
		setRoot("UserName", UserName);

		setRoot("navbar", navbar());

		setRoot("menu", menu());

		super.View();
	}

}
