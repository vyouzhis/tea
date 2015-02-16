package com.lib.thread;

import org.ppl.BaseClass.BaseCronThread;

public class loopThread extends BaseCronThread{

	@Override
	public void Run() {
		// TODO Auto-generated method stub
		echo("i am a loop thread!");
	}

	@Override
	public int minute() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public int hour() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int day() {
		// TODO Auto-generated method stub
		return 0;
	}

}
