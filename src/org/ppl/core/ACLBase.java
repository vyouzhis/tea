package org.ppl.core;

import java.util.Map;

public class ACLBase extends ACLInit {

	public boolean aclCheckAccess() {
		int uid = aclGetUid();
		String cm = aclgetCM();
		
		String format = "SELECT uid FROM `"+ mConfig.GetValue("db_pre_rule")
				+ "user_info` WHERE uid =%d and cm='%s' LIMIT 1";
		String sql = String.format(format, uid, cm);
		Map<String, Object> res = FetchOne(sql);
		if(res != null) return true;
		else {
			aclLogout();
			return false;
		}
	}
}