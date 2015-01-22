package com.lib.manager.setting;

import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BasePerminterface;
import org.ppl.BaseClass.Permission;
import org.ppl.etc.UrlClassList;

public class admin_permission_list extends Permission implements
		BasePerminterface {
	private List<String> rmc;
	
	public admin_permission_list() {
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

		Default();

		if(rmc.size() == 2){
			switch (rmc.get(1).toString()) {
			case "read":
				read(null);
				break;
			case "create":
				create(null);
				break;
			default:
				break;
			}
		}
		// aclLoadLib();
		super.View();
	}

	private void Default() {

		setRoot("static_uri", porg.getContext_Path());

		String UserName = aclgetName();
		setRoot("UserName", UserName);

		setRoot("navbar", navbar());

		setRoot("menu", menu());

		setRoot("name", _MLang("name"));

		
		setRoot("fun", this);
	}

	@Override
	public void read(Object arg) {
		// TODO Auto-generated method stub
		echo("read");
		
		UrlClassList ucl = UrlClassList.getInstance();
		Map<String, List<String>> PackClassList;
		PackClassList = ucl.getPackClassList();

		setRoot("Pack_Class_List", PackClassList);
	}

	@Override
	public void create(Object arg) {
		// TODO Auto-generated method stub

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
		this.rmc = arg;
	}
}
