package com.lib.thread;

import org.ppl.BaseClass.BaseThread;

public class testThread extends BaseThread {

	@Override
	public void Run() {
		// TODO Auto-generated method stub
		echo("hi i am test turead");
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

}