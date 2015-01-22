package com.lib.manager.setting;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ppl.BaseClass.BasePerminterface;
import org.ppl.BaseClass.Permission;
import org.ppl.common.PorG;
import org.ppl.common.ShowMessage;
import org.ppl.etc.Config;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;
import org.ppl.io.TimeClass;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.util.Hash;

public class admin_permission_setting extends Permission implements
		BasePerminterface {
	private List<String> argList;

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

		def();
				
		if (argList.size() == 2) {
			switch (argList.get(1).toString()) {
			case "read":
				read(null);
				break;
			case "create":
				create(null);
				UrlClassList ucl = UrlClassList.getInstance();
				String bad_url = ucl.read("admin_permission_list");

				ShowMessage ms = ShowMessage.getInstance();
				String res = ms.SetMsg(bad_url, _CLang("ok_save"), 3000);
				super.setHtml(res);
				return;
			default:
				break;
			}
		}
		

		super.View();
	}

	@Override
	public void read(Object arg) {
		// TODO Auto-generated method stub
		UrlClassList ucl = UrlClassList.getInstance();
		Map<String, List<String>> PackClassList;
		PackClassList = ucl.getPackClassList();
		echo(PackClassList);
		
		setRoot("Pack_Class_List", PackClassList);
	}

	private void def() {
		setRoot("static_uri", porg.getContext_Path());

		String UserName = aclgetName();
		setRoot("UserName", UserName);

		setRoot("navbar", navbar());

		setRoot("menu", menu());

		setRoot("name", _MLang("name"));

		Config mConfig = new Config(globale_config.Config);
		String subRole = mConfig.GetValue(globale_config.SubRole);
		JSONObject subJson = JSON.parseObject(subRole);
		Map<String, String> subMap = new HashMap<String, String>();

		for (Entry<String, Object> entry : subJson.entrySet()) {
			String key = entry.getKey();
			Object val = entry.getValue();
			subMap.put(key, val.toString());
		}

		setRoot("subMap", subMap);
		
		setRoot("fun", this);
	}
	
	@Override
	public void create(Object arg) {
		// TODO Auto-generated method stub
		
		Map<String, String> role_pg = porg.getAllpg();
		String role_json = "";
		Map<String, Map<String, String>> role = new HashMap<String, Map<String, String>>();
		Map<String, String> sub;
		String group_name = role_pg.get("group_name");
		String group_desc = role_pg.get("group_desc");

		Config mConfig = new Config(globale_config.Config);
		String subRole = mConfig.GetValue(globale_config.SubRole);
		JSONObject subJson = JSON.parseObject(subRole);
		List<String> subList = new ArrayList<String>();

		for (Entry<String, Object> entry : subJson.entrySet()) {
			String key = entry.getKey();
			if (role_pg.containsKey(key)) {
				subList.add(key);
				role_pg.remove(key);
			}
		}
		String subRoles = "";
		if (subList.size() > 0) {
			subRoles = JSON.toJSONString(subList);
		}

		role_pg.remove("group_name");
		role_pg.remove("group_desc");
		role_pg.remove("gid");

		String sub_name, sub_action;
		for (Entry<String, String> entry : role_pg.entrySet()) {
			String key = entry.getKey();
			// String value = entry.getValue();
			String[] role_split = key.split("_", 2);
			echo(key);
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
		TimeClass tc = TimeClass.getInstance();
		int now = (int)tc.time();
		String format = "INSERT INTO `tea`.`"
				+ mConfig.GetValue("db_pre_rule")
				+ "group` "
				+ "(`gname`, `gdesc`, `mainrole`, `subrole`,`uid`,`ctime`, `etime`)"
				+ " VALUES ('%s', '%s',  '%s', '%s', %d, %d, %d);";
		String sql = String.format(format, group_name, group_desc, role_json,
				subRoles, aclGetUid(), now, now);

		try {
			update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
