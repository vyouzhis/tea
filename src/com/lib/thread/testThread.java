package com.lib.thread;

import java.util.List;
import java.util.Map;

import org.ppl.BaseClass.BaseThread;

public class testThread extends BaseThread {

	@Override
	public void Run() {
		// TODO Auto-generated method stub
		String sql = "select * from web_user limit 1";
		Map<String, Object> res = FetchOne(sql);
		echo(res);
		echo("hi i am test thread !!!!!!!!!!!");
	}

	@Override
	public boolean isRun() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Stop() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void mailbox(Object o) {
		// TODO Auto-generated method stub
		
		List<String> s = (List<String>) o;
		for(String k:s){
			echo(k);
		}
	}

}