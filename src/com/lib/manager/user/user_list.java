package com.lib.manager.user;

import org.ppl.BaseClass.BasePerminterface;
import org.ppl.BaseClass.Permission;

public class user_list extends Permission implements
BasePerminterface {

	public user_list() {
		// TODO Auto-generated constructor stub
		String className = this.getClass().getCanonicalName();
		// stdClass = className;
		super.GetSubClassName(className);
	}
	
	public void Show() {};
	
}
