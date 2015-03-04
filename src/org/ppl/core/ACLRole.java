package org.ppl.core;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.etc.UrlClassList;
import org.ppl.io.TimeClass;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ACLRole extends ACLBase {

	@SuppressWarnings("unchecked")
	public boolean checkRole() {
		List<String> rmc = porg.getRmc();
		if (rmc.size() < 2)
			return true;
		String action = rmc.get(1).toString();
		String libName = rmc.get(0).toString();
		Map<String, String> act = new HashMap<>();
		act.put("read", "0");
		act.put("create", "1");
		act.put("edit", "2");
		act.put("remove", "3");
		act.put("search", "4");

		String role = aclfetchMyRole();
		JSONObject jsonRole = JSON.parseObject(role);
		for (String key : jsonRole.keySet()) {
			Object libObject = jsonRole.get(key);

			Map<String, Object> lib = JSON.parseObject(libObject.toString(),
					Map.class);
			if (lib.containsKey(libName)) {
				String myAct = lib.get(libName).toString();
				String search = act.get(action);
				if (search == null)
					return false;
				if (myAct.matches("(.*)" + act.get(action) + "(.*)")) {
					return true;
				}
			}
		}
		return false;
	}

	public int RoleUpdate() {
		int uid = aclGetUid();
		int gid = aclGetGid();
		if (uid != 1)
			return -1;
		Map<String, Map<String, String>> AdminRole = new HashMap<String, Map<String, String>>();
		String RoleVal = "0_1_2_3_4";

		UrlClassList ucl = UrlClassList.getInstance();
		Map<String, List<String>> PackClassList;
		PackClassList = ucl.getPackClassList();
		// echo(JSON.toJSONString(PackClassList));
		for (String key : PackClassList.keySet()) {
			Map<String, String> subRole = new HashMap<>();
			for (int i = 0; i < PackClassList.get(key).size(); i++) {
				subRole.put(PackClassList.get(key).get(i), RoleVal);
			}
			AdminRole.put(key, subRole);
		}
		String MainRole = JSON.toJSONString(AdminRole);

		String format = "SELECT id FROM `"+DB_PRE+"group`  where id=" + gid;
		Map<String, Object> res;
		res = FetchOne(format);
		TimeClass tc = TimeClass.getInstance();
		int now = (int) tc.time();
		String sql = "";
		if (res == null) {
			format = "INSERT INTO `tea`.`"
					+ DB_PRE
					+ "group` "
					+ "(`id`,`gname`, `gdesc`, `mainrole`, `subrole`,`uid`,`ctime`, `etime`)"
					+ " VALUES (%d,'%s', '%s',  '%s', '%s', %d, %d, %d);";
			sql = String.format(format, gid, "default name", "default desc",
					MainRole, "", uid, now, now);

		} else {
			format = "UPDATE `tea`.`" + DB_PRE
					+ "group` SET " + " `mainrole` = '%s',  `etime` = '%d' "
					+ "WHERE `role_group`.`id` = %d;";

			sql = String.format(format, MainRole, now, gid);

		}
		
		try {
			update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
}
