package com.lib.manager.user;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BasePerminterface;
import org.ppl.BaseClass.Permission;
import org.ppl.common.ShowMessage;
import org.ppl.etc.Config;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;

public class user_list extends Permission implements BasePerminterface {
	private List<String> rmc;

	public user_list() {
		// TODO Auto-generated constructor stub
		String className = this.getClass().getCanonicalName();
		// stdClass = className;
		super.GetSubClassName(className);
		setRoot("name", _MLang("name"));

		setRoot("fun", this);
	}

	public void Show() {
		if (super.Init() == -1)
			return;
		Default();
		rmc = porg.getRmc();
		if (rmc.size() == 2) {
			switch (rmc.get(1).toString()) {
			case "read":
				read(null);
				break;
			default:
				Msg(_CLang("error_role"));
				return;
			}
		}
		super.View();

	};

	@Override
	public void read(Object arg) {
		// TODO Auto-generated method stub
		
		Config mConfig = new Config(globale_config.Config);
		String sql = "SELECT uid,name,nickname,email,"
				+ "from_unixtime(ctime) as ctime , from_unixtime(ltime) as ltime FROM "
				+ "`" + mConfig.GetValue("db_pre_rule")
				+ "user_info` where cid=" + aclGetUid();
		
		List<Map<String, Object>> res ;
		try {
			res = FetchAll(sql);
			setRoot("list_user", res);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UrlClassList ucl = UrlClassList.getInstance();
		setRoot("edit_url", ucl.read("user_profile"));
	}

	private void Default() {
		UrlClassList ucl = UrlClassList.getInstance();
		setRoot("create_url", ucl.read("user_profile"));
	}

	private void Msg(String msg) {
		UrlClassList ucl = UrlClassList.getInstance();
		String ok_url = ucl.read("user_list");

		ShowMessage ms = ShowMessage.getInstance();
		String res = ms.SetMsg(ok_url, msg, 3000);
		super.setHtml(res);
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
}
