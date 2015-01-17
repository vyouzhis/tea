package org.ppl.core;

import java.util.Map;

import com.alibaba.fastjson.JSON;

public class ACLControl extends ACLRole {

	private void aclfetchRole() {
		int uid = aclGetUid();
		String format = "SELECT g.permissions FROM "
				+ mConfig.GetValue("db_pre_rule") + "user_info u, "
				+ mConfig.GetValue("db_pre_rule")
				+ "group g where u.gid =  g.id and u.uid=%d LIMIT 1";
		String sql = String.format(format, uid);
		
		Map<String, Object> res = FetchOne(sql);
		if(res!=null){
			String json = JSON.toJSONString(res);
			
			SessAct.SetSession("role", json);
		}
	}
}
