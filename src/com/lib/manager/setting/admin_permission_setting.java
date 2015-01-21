package com.lib.manager.setting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ppl.BaseClass.BasePerminterface;
import org.ppl.BaseClass.Permission;
import org.ppl.common.PorG;
import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;

import com.alibaba.fastjson.JSON;
import com.mongodb.util.Hash;

public class admin_permission_setting extends Permission implements
		BasePerminterface {
	private List<String> argList;
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

		root.put("name", _MLang("name"));

		UrlClassList ucl = UrlClassList.getInstance();
		Map<String, List<String>> PackClassList;
		PackClassList = ucl.getPackClassList();

		root.put("Pack_Class_List", PackClassList);
		root.put("fun", this);

		if (argList.get(1).toString().equals("create")) {
			create(null);
			String bad_url = ucl.read("admin_permission_list");

			ShowMessage ms = ShowMessage.getInstance();
			String res = ms.SetMsg(bad_url, _CLang("ok_save"), 3000);
			super.setHtml(res);
			return;

		} else if (argList.get(1).toString().equals("edit")) {

		} else if (argList.get(1).toString().equals("remove")) {

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
		Map<String, String> role_pg = pg.getAllpg();
		String role_json = "";
		Map<String, Map<String, String>> role = new HashMap<String, Map<String, String>>();
		Map<String, String> sub;
		role_pg.remove("group_name");
		role_pg.remove("group_desc");
		role_pg.remove("gid");
		String sub_name, sub_action;
		for (Entry<String, String> entry : role_pg.entrySet()) {
			String key = entry.getKey();
			// String value = entry.getValue();
			String[] role_split = key.split("_", 2);
			sub_name = role_split[1].substring(0, role_split[1].length() - 2);
			sub_action = role_split[1].substring(role_split[1].length() - 1);

			if (!role.containsKey(role_split[0])) {
				sub = new HashMap<String, String>();
			} else {
				sub = role.get(role_split[0]);
				if (sub.containsKey(sub_name)) {
					sub_action += "_" + sub.get(sub_name);
				}
			}
			sub.put(sub_name, sub_action);
			role.put(role_split[0], sub);
		}
		role_json = JSON.toJSONString(role);
		echo(role_json);
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
