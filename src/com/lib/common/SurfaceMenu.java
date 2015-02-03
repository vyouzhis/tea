package com.lib.common;

import org.ppl.BaseClass.BaseModule;

public class SurfaceMenu extends BaseModule{
	private String className = null;
	
	public SurfaceMenu() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
	}
	
	@Override
	public void filter() {
		// TODO Auto-generated method stub
		if(igetUid()>0){
			setRoot("uid", igetUid());
		}
		super.View();
	}

}
