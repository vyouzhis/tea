package com.lib.common;

import java.util.HashMap;
import java.util.Map;

import org.ppl.BaseClass.BaseModule;
import org.ppl.common.PorG;

public class Menu extends BaseModule {
	private String className = null;
	private Map<String, Object> root = null;
	
	public Menu() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		GetSubClassName(className);
	}
	
	@Override
	public void filter() {
		// TODO Auto-generated method stub
		super.View(Data());
	}

	private Map<String, Object> Data() {

		if(root==null){
			PorG pg = PorG.getInstance();
			root = new HashMap<String, Object>();			
			root.put("static_uri", pg.getContext_Path());
		}
		
		return root;
	}

	public Map<String, Object> getRoot() {
		return root;
	}

	public void setRoot(Map<String, Object> root) {
		this.root = root;
	}
}
