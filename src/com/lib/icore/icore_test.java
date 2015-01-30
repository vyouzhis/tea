package com.lib.icore;

import org.ppl.BaseClass.BaseiCore;

public class icore_test extends BaseiCore {
	private String className = null;
	public icore_test() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}
	
	@Override
	public void Show() {
		// TODO Auto-generated method stub
		
		if(super.Init()==-1){
			
			return ;
		}
		super.View();
	}
}
