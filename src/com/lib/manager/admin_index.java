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

		super.View();
	}

}
