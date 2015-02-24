package org.ppl.core;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.ppl.db.DBSQL;
import org.ppl.etc.globale_config;
import org.ppl.io.Encrypt;
import org.ppl.io.TimeClass;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ACLInit extends DBSQL {
	
	public ACLInit() {
		// TODO Auto-generated constructor stub
		
		SetCon();
	}

	public int aclGetUid() {
		String uid = getInfo("Uid");
		if (uid == null)
			return 0;
		return Integer.valueOf(uid);
	}

	public String aclGetPhone() {
		return getInfo("phone");
	}

	public String aclGetNickName() {
		return getInfo("NickName");
	}

	public String aclGetEmail() {
		return getInfo("email");
	}

	public int aclGetGid() {
		return Integer.valueOf(getInfo("gid"));
	}
	
	public String aclGetName() {
		return getInfo("Name");
	}

	public String aclgetCM() {

		return getInfo("CM");
	}

	public String aclfetchMyRole() {

		return getInfo("main_role");
	}

	private String getInfo(String key) {
		String aclSess = SessAct.GetSession(mConfig
				.GetValue(globale_config.sessAcl));
		// Object actJson = null;
		if (aclSess == null)
			return null;
		JSONObject actJson = JSON.parseObject(aclSess);
		if (actJson == null)
			return null;
		return actJson.getString(key);
	}

	private void ErrorPWD(String name) {
		
		TimeClass tc = TimeClass.getInstance();
		int now = (int) tc.time();
		
		String format = "UPDATE `tea`.`"
				+ mConfig.GetValue("db_pre_rule")
				+ "user_info` SET `error` = `error`+1, `ltime`=%d WHERE `name` = '%s' LIMIT 1";
		
		String sql = String.format(format, now, name);
		try {
			update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int aclLogin(String name, String passwd, String get_salt) {
		
		String format = "select uid, cm, passwd,nickname,phone,email,ltime,error,gid  from "
				+ mConfig.GetValue("db_pre_rule")
				+ "user_info WHERE  name='%s' limit 1";
		String sql = String.format(format, name);
		Encrypt en = Encrypt.getInstance();
		TimeClass tc = TimeClass.getInstance();

		Map<String, Object> res = FetchOne(sql);

		if (res != null) {
			if (!passwd.equals(en.MD5(res.get("passwd") + get_salt))) {
				ErrorPWD(name);
				return -2;
			}
			
			int ltime = Integer.valueOf(res.get("ltime").toString());
			int error = Integer.valueOf(res.get("error").toString());
			int now = (int) tc.time();
			
			int delay = mConfig.GetInt(globale_config.TimeDelay);
			if(now-ltime < delay && error >2)return -3;
			
			String new_cm = en.MD5(now + "");
			format = "UPDATE " + mConfig.GetValue("db_pre_rule")
					+ "user_info SET `cm` = '%s', `ltime`=%d, `error`=0 WHERE `uid`='%d';";
			sql = String.format(format, new_cm, now, res.get("uid"));

			try {
				update(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<String, String> UserSess = new HashMap<String, String>();
			UserSess.put("Uid", res.get("uid").toString());
			UserSess.put("NickName", res.get("nickname").toString());
			UserSess.put("phone", res.get("phone").toString());
			UserSess.put("email", res.get("email").toString());
			UserSess.put("gid", res.get("gid").toString());
			UserSess.put("Name", name);
			UserSess.put("CM", new_cm);

			
			SessAct.SetSession(mConfig.GetValue(globale_config.Ontime), now
					+ "");

			format = "SELECT g.mainrole,g.subrole FROM "
					+ mConfig.GetValue("db_pre_rule") + "user_info u, "
					+ mConfig.GetValue("db_pre_rule")
					+ "group g where u.gid =  g.id and u.uid=%s LIMIT 1";
			sql = String.format(format, res.get("uid").toString());

			Map<String, Object> role = FetchOne(sql);
			if (role != null) {
				UserSess.put("main_role", role.get("mainrole").toString());
				UserSess.put("sub_role", role.get("subrole").toString());
			}else {
				return -3;
			}

			String json = JSON.toJSONString(UserSess);

			SessAct.SetSession(mConfig.GetValue(globale_config.sessAcl), json);

			return 0;
		}

		return -1;
	}

	public void aclLogout() {
		SessAct.SetSession(mConfig.GetValue(globale_config.sessAcl), "");
	}
}
