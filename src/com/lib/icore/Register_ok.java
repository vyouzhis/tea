package com.lib.icore;

import java.util.List;

import org.ppl.BaseClass.BaseSurface;

public class Register_ok extends BaseSurface {
	private String className = null;
	public Register_ok() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}
	
	@Override
	public void Show() {
		// TODO Auto-generated method stub
		List<String> rmc = porg.getRmc();
		echo(rmc);
		super.View();
	}
}
