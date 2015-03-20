package org.ppl.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ppl.io.TimeClass;

public class SystemLog extends PObject {

	/**
	 * @since log to db, and return log sql
	 * @param uid
	 * @return
	 */
	public String Log(int uid) {
		
		String format = "INSERT INTO `tea`.`" + mConfig.GetValue("db.rule.ext")
				+ "log` " + "(`lid`, `uid`, `action`, `ip`, `ctime`)"
				+ " VALUES ('%s', '%d', '%s', '%s', '%d');";

		TimeClass tc = TimeClass.getInstance();
		int now = (int) tc.time();
		String ip = porg.GetIP();
		List<String> rmc = porg.getRmc();
		String lib =  rmc.get(0);
		String action = "";
		int a = 0;
		Map<String, Integer> act = new HashMap<>();
		act.put("read", 0);
		act.put("create", 1);
		act.put("edit", 2);
		act.put("remove", 3);
		act.put("search", 4);
		
		if(rmc.size()>1){
			action = rmc.get(1) ;
			a = act.get(action);
		}
		
		String sql = String.format(format, lib, uid, a,ip, now);
		
		return sql;
	}
}
