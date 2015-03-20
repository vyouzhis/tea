package com.lib.thread;

import java.util.Map;

import org.ppl.BaseClass.BaseRapidThread;

public class rapi_two extends BaseRapidThread {
	private Map<String, String> ml;
	@Override
	public void Run() {
		// TODO Auto-generated method stub
		for (String key:ml.keySet()) {
			echo("key:"+key +"  val:"+ml.get(key));
		}
		
		long millis = (long)randomWithRange(1, 10)*1000;
		
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	@SuppressWarnings("unchecked")
	@Override
	public void mailbox(Object o) {
		// TODO Auto-generated method stub
		ml = (Map<String, String>)o;
				
	}
	
	private int randomWithRange(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}

}
