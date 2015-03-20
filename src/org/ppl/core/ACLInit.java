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

	/**
	 * @since manager acl get uid
	 * @return
	 */
	public int aclGetUid() {
		String uid = getInfo("Uid");
		if (uid == null)
			return 0;
		return Integer.valueOf(uid);
	}

	/**
	 * @since manager acl get phone
	 * @return
	 */
	public String aclGetPhone() {
		return getInfo("phone");
	}

	/**
	 * @since manager acl get nick name
	 * @return
	 */
	public String aclGetNickName() {
		return getInfo("NickName");
	}

	/**
	 * @since manager acl get email
	 * @return
	 */
	public String aclGetEmail() {
		return getInfo("email");
	}

	/**
	 * @since manager acl get group id
	 * @return
	 */
	public int aclGetGid() {
		return Integer.valueOf(getInfo("gid"));
	}

	/**
	 * @since manager acl get login name
	 * @return
	 */
	public String aclGetName() {
		return getInfo("Name");
	}

	/**
	 * @since manager acl get cm
	 * @return
	 */
	public String aclgetCM() {

		return getInfo("CM");
	}

	/**
	 * @since manager acl fetch my role
	 * @return
	 */
	public String aclfetchMyRole() {

		return getInfo("main_role");
	}

	/**
	 * @since manager acl get info
	 * @param key
	 * @return
	 */
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

	/**
	 * @since manager update error password count
	 * @param name
	 */
	private void ErrorPWD(String name) {

		TimeClass tc = TimeClass.getInstance();
		int now = (int) tc.time();

		String format = "UPDATE `tea`.`"
				+ DB_PRE
				+ "user_info` SET `error` = `error`+1, `ltime`=%d WHERE `name` = '%s' LIMIT 1";

		String sql = String.format(format, now, name);
		try {
			update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @since manager acl login
	 * @param name
	 * @param passwd
	 * @param get_salt
	 * @return
	 */
	public int aclLogin(String name, String passwd, String get_salt) {

		String format = "select uid, cm, passwd,nickname,phone,email,ltime,error,gid  from "
				+ DB_PRE + "user_info WHERE  name='%s' limit 1";
		String sql = String.format(format, name);
		Encrypt en = Encrypt.getInstance();

		Map<String, Object> res = FetchOne(sql);

		if (res != null) {
			int ltime = Integer.valueOf(res.get("ltime").toString());
			int error = Integer.valueOf(res.get("error").toString());
			int now = time();


			int delay = mConfig.GetInt(globale_config.TimeDelay);
			if (now - ltime < delay && error > 2)
				return -3;

			String pwd = en.MD5(res.get("passwd") + get_salt);

			if (!passwd.equals(pwd)) {
				ErrorPWD(name);
				return -2;
			}

			String new_cm = en.MD5(now + "");
			format = "UPDATE "
					+ DB_PRE
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

			format = "SELECT g.mainrole,g.subrole FROM " + DB_PRE
					+ "user_info u, " + DB_PRE
					+ "group g where u.gid =  g.id and u.uid=%s LIMIT 1";
			sql = String.format(format, res.get("uid").toString());

			Map<String, Object> role = FetchOne(sql);
			if (role != null) {
				UserSess.put("main_role", role.get("mainrole").toString());
				UserSess.put("sub_role", role.get("subrole").toString());
			}
			else {
				return -4;
			}

			String json = JSON.toJSONString(UserSess);

			SessAct.SetSession(mConfig.GetValue(globale_config.sessAcl), json);

			return 0;
		}

		return -1;
	}

	/**
	 * @since manager acl logout
	 */
	public void aclLogout() {
		SessAct.SetSession(mConfig.GetValue(globale_config.sessAcl), "");
	}
}
