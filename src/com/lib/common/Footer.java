package com.lib.common;

import java.util.Map;

import org.ppl.BaseClass.BaseModule;
import org.ppl.common.PorG;

public class Footer extends BaseModule {
	private String className = null;
	private Map<String, Object> root = null;

	public Footer() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
	}

	@Override
	public void filter() {
		// TODO Auto-generated method stub
		Data();
		super.View();
	}

	private void Data() {
		
		setRoot("static_uri", porg.getContext_Path());

	}

}
