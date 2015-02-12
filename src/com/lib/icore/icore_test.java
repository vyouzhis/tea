package com.lib.icore;

import java.sql.SQLException;
import java.util.Map;

import org.ppl.BaseClass.BaseiCore;

public class icore_test extends BaseiCore {
	private String className = null;
	public icore_test() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}
	
	@Override
	public void Show() {
		// TODO Auto-generated method stub
		
		if(super.Init()==-1){
			
			return ;
		}
		
		String sql = "select * from web_user limit 1";
		Map<String, Object> res = FetchOne(sql);
		echo(res);
		sql = "update web_user set alias='h@hh.com' where id=1 limit 1;";
		try {
			update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//rollback();
		sql = "select * from web_user limit 1";
		res = FetchOne(sql);
		echo(res);
		super.View();
	}
}
