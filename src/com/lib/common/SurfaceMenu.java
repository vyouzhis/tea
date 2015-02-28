package com.lib.common;

import java.util.List;

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
		List<String> rmc = porg.getRmc();
		setRoot("SurfaceMenu", rmc.get(0));
		super.View();
	}

}
