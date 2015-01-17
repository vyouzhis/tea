package com.lib.manager.setting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ppl.BaseClass.BasePerminterface;
import org.ppl.BaseClass.Permission;
import org.ppl.common.PorG;

public class admin_permission_setting extends Permission implements BasePerminterface {
	private List<String> argList ;
	private Map<String, Object> root;
	public admin_permission_setting() {
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
		root = new HashMap<String, Object>();
		PorG pg = PorG.getInstance();

		root.put("static_uri", pg.getContext_Path());

		String UserName = aclgetName();
		root.put("UserName", UserName);

		root.put("navbar", navbar());

		root.put("menu", menu());
	
		if (argList.get(1).toString().equals("create")) {
			create(null);
		}else if (argList.get(1).toString().equals("edit")) {
			
		}else if (argList.get(1).toString().equals("remove")) {
			
		}
		
		super.View(root);
	}
	
	@Override
	public void read(Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(Object arg) {
		// TODO Auto-generated method stub
		PorG pg = PorG.getInstance();
		String group_name = pg.porg("group_name");
		String group_desc = pg.porg("group_desc");
		Map<String, String> Allporg = pg.getAllpg();
		Allporg.remove("gid");
		Allporg.remove("group_name");
		Allporg.remove("group_desc");
		for(Entry<String, String> entry : Allporg.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    echo(key+"--"+value);
		}
	}

	@Override
	public void edit(Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void search(Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void UrlServlet(List<String> arg) {
		// TODO Auto-generated method stub
		argList = arg;
		
	}

}

