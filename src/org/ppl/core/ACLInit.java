package org.ppl.core;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.ppl.etc.Config;
import org.ppl.etc.globale_config;
import org.ppl.io.Encrypt;
import org.ppl.io.TimeClass;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ACLInit extends PObject {
	protected Config mConfig = null;

	public ACLInit() {
		// TODO Auto-generated constructor stub
		mConfig = new Config(globale_config.Config);
	}

	public int aclGetUid() {
		String aclSess = SessAct.GetSession(mConfig
				.GetValue(globale_config.sessAcl));
		// Object actJson = null;
		if(aclSess==null)return 0;
		JSONObject actJson = JSON.parseObject(aclSess);
		// echo(actJson.size());
		if (actJson.get("Uid") != null)
			return actJson.getIntValue("Uid");
		return 0;
	}

	public String aclgetNickName() {
		String aclSess = SessAct.GetSession(mConfig
				.GetValue(globale_config.sessAcl));
		// Object actJson = null;
		
		JSONObject actJson = JSON.parseObject(aclSess);

		return actJson.getString("NickName");
	}

	public String aclgetName() {
		String aclSess = SessAct.GetSession(mConfig
				.GetValue(globale_config.sessAcl));
		// Object actJson = null;
		JSONObject actJson = JSON.parseObject(aclSess);

		return actJson.getString("Name");
	}

	public String aclgetCM() {
		String aclSess = SessAct.GetSession(mConfig
				.GetValue(globale_config.sessAcl));
		// Object actJson = null;
		JSONObject actJson = JSON.parseObject(aclSess);

		return actJson.getString("CM");
	}
	
	public String aclfetchMyRole() {
		String aclSess = SessAct.GetSession(mConfig
				.GetValue(globale_config.sessAcl));
		// Object actJson = null;
		JSONObject actJson = JSON.parseObject(aclSess);

		return actJson.getString("role");
	}

	public int aclLogin(String name, String passwd, String get_salt) {
		Config mConfig = new Config(globale_config.Config);
		String format = "select uid, cm, passwd,nickname  from "
				+ mConfig.GetValue("db_pre_rule")
				+ "user_info WHERE  name='%s' limit 1";
		String sql = String.format(format, name);
		Encrypt en = Encrypt.getInstance();
		TimeClass tc = TimeClass.getInstance();

		Map<String, Object> res = FetchOne(sql);

		if (res != null) {
			if (!passwd.equals(en.MD5(res.get("passwd") + get_salt))) {
				echo("passwd:"+passwd+" md5:");
				return -2;
			}
			
			long now_time = tc.time();
			String new_cm = en.MD5(now_time + "");
			format = "UPDATE " + mConfig.GetValue("db_pre_rule")
					+ "user_info SET `cm` = '%s', `ltime`=%d WHERE `uid`='%d';";
			sql = String.format(format, new_cm, now_time, res.get("uid"));

			try {
				update(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<String, String> UserSess = new HashMap<String, String>();
			UserSess.put("Uid", res.get("uid").toString());
			UserSess.put("NickName", res.get("nickname").toString());
			UserSess.put("Name", name);
			UserSess.put("CM", new_cm);
			
			 format = "SELECT g.permissions,g.subpermiss FROM "
					+ mConfig.GetValue("db_pre_rule") + "user_info u, "
					+ mConfig.GetValue("db_pre_rule")
					+ "group g where u.gid =  g.id and u.uid=%s LIMIT 1";
			 sql = String.format(format, res.get("uid").toString());
			
			Map<String, Object> role = FetchOne(sql);
			if(role!=null){				
				UserSess.put("main_role", role.get("permissions").toString());
				UserSess.put("sub_role", role.get("subpermiss").toString());
			}
						
			String json = JSON.toJSONString(UserSess);
			
			SessAct.SetSession(mConfig.GetValue(globale_config.sessAcl), json);

			return 0;
		}

		return -1;
	}
	
	public boolean aclmyRole(String action) {
		Map<String, Integer> act = new HashMap<String, Integer>();
		act.put("read", 0);
		act.put("create", 0);
		act.put("edit", 0);
		act.put("remove", 0);
		act.put("search", 0);
		
		String role = aclfetchMyRole();
		if(role==null) return false;
		JSONObject roleJson = JSON.parseObject(role);
		
		
		return true;
	}

	public void aclLogout() {
		SessAct.SetSession(mConfig.GetValue(globale_config.sessAcl), "");
	}
}

