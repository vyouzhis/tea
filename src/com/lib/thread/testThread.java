package com.lib.thread;

import java.util.List;

import org.ppl.BaseClass.BaseThread;

public class testThread extends BaseThread {

	@Override
	public void Run() {
		// TODO Auto-generated method stub
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
	public void postMsg(Object o) {
		// TODO Auto-generated method stub
		
		List<String> s = (List<String>) o;
		for(String k:s){
			echo(k);
		}
	}

}