package com.lib.common;

import org.ppl.BaseClass.BaseModule;
import org.ppl.common.PorG;

public class Header extends BaseModule {
	private String className = null;

	public Header() {
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

		PorG pg = PorG.getInstance();

		setRoot("static_uri", pg.getContext_Path());

	}

}
