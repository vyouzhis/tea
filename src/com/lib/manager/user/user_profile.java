package com.lib.manager.user;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.internal.compiler.lookup.VoidTypeBinding;
import org.ppl.BaseClass.BasePerminterface;
import org.ppl.BaseClass.Permission;
import org.ppl.common.ShowMessage;
import org.ppl.etc.Config;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;
import org.ppl.io.Encrypt;
import org.ppl.io.TimeClass;

public class user_profile extends Permission implements BasePerminterface {
	private List<String> rmc;

	public user_profile() {
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
		rmc = porg.getRmc();
		if (rmc.size() == 2) {
			switch (rmc.get(1).toString()) {
			case "read":
				read(null);
				break;
			case "create":
				create(null);
				return;
			case "edit":
				edit(null);
				return;
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
		super.read(arg);
		ListGroup();
		UrlClassList ucl = UrlClassList.getInstance();
		String uid = porg.getKey("uid");
		if (uid == null) {

			setRoot("action_url", ucl.create(SliceName(stdClass)));
		} else {
			getUserInfo(uid);
			setRoot("action_url", ucl.edit(SliceName(stdClass)));
		}

	}

	private void ListGroup() {
		int gid = aclGetUid();
		String sql = "SELECT id,gname,gdesc FROM `"
				+ mConfig.GetValue("db_pre_rule") + "group` where uid=" + gid;
		List<Map<String, Object>> res = null;

		try {
			res = FetchAll(sql);
			if (res.size() > 0) {
				setRoot("list_group", res);
				setRoot("group_id", res.get(0).get("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getUserInfo(String uid) {
		Config mConfig = new Config(globale_config.Config);
		String sql = "SELECT * FROM `" + mConfig.GetValue("db_pre_rule")
				+ "user_info`  WHERE uid=" + uid;

		Map<String, Object> res;
		res = FetchOne(sql);
		if (res != null) {
			setRoot("uid", res.get("uid"));
			setRoot("nickname", res.get("nickname"));
			setRoot("username", res.get("name"));
			setRoot("email", res.get("email"));
			setRoot("group_id", res.get("gid"));
		}
	}

	@Override
	public void create(Object arg) {
		// TODO Auto-generated method stub
		super.create(arg);
		Config mConfig = new Config(globale_config.Config);
		String nickname = porg.getKey("nickname");
		String username = porg.getKey("username");
		String email = porg.getKey("email");
		String pass1 = porg.getKey("pass1");
		String pass2 = porg.getKey("pass2");
		String group = porg.getKey("group");

		if (!pass1.equals(pass2)) {
			Msg(_CLang("error_pwdneq"));
			return;
		}

		Encrypt en = Encrypt.getInstance();
		String pwd = en.MD5(pass1);

		TimeClass tc = TimeClass.getInstance();
		int now = (int) tc.time();

		String format = "INSERT INTO `tea`.`"
				+ mConfig.GetValue("db_pre_rule")
				+ "user_info` "
				+ "(`name`, `passwd`, `cm`, `nickname`, `email`, `ctime`, `ltime`,  `gid`, `cid`)"
				+ " VALUES ( '%s', '%s', '%s', '%s', '%s', '%d', '%d', '%s', '%d')";
		String sql = String.format(format, username, pwd, "", nickname, email,
				now, now, group, aclGetUid());

		try {
			update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Msg(_CLang("ok_save"));
	}

	@Override
	public void edit(Object arg) {
		// TODO Auto-generated method stub
		super.edit(arg);

		Config mConfig = new Config(globale_config.Config);
		String nickname = porg.getKey("nickname");
		String username = porg.getKey("username");
		String email = porg.getKey("email");
		String pass1 = porg.getKey("pass1");
		String pass2 = porg.getKey("pass2");
		String group = porg.getKey("group");
		String uid = porg.getKey("edit_id");
		String pwd = "";
		if (!pass1.isEmpty() && !pass2.isEmpty()) {
			if (!pass1.equals(pass2)) {
				Msg(_CLang("error_pwdneq"));
				return;
			}
			Encrypt en = Encrypt.getInstance();
			pwd = ", `passwd`='"+en.MD5(pass1)+"' ";
		}
		

		TimeClass tc = TimeClass.getInstance();
		int now = (int) tc.time();
		
		String format = "UPDATE `tea`.`"
				+ mConfig.GetValue("db_pre_rule")
				+ "user_info` SET `name` = '%s', `nickname` = '%s', `gid` = '%s'" +
				pwd +
				" WHERE `role_user_info`.`uid` = %s";
		String sql = String.format(format, username, nickname, group, uid);
		try {
			update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Msg(_CLang("ok_save"));
	}

	private void Msg(String msg) {
		UrlClassList ucl = UrlClassList.getInstance();
		String ok_url = ucl.read("user_list");

		ShowMessage ms = ShowMessage.getInstance();
		String res = ms.SetMsg(ok_url, msg, 3000);
		super.setHtml(res);
	}
}