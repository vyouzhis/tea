package org.ppl.core;

import java.util.Date;
import java.util.Map;

import org.ppl.BaseClass.BaseThread;
import org.ppl.BaseClass.LibThread;
import org.ppl.Module.ModuleBind;
import org.ppl.etc.globale_config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class RapidThread extends LibThread {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("rapidthread run");
		ListQueue();
	}

	private void ListQueue() {
		while (true) {
			synchronized (globale_config.RapidListQueue) {
				try {
					System.out.println("Waiter is waiting for the notifier at " + new Date());
					globale_config.RapidListQueue.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("listqueue!");
			for (Map.Entry<String, Object> entry : globale_config.RapidListQueue.entrySet()) {
				//System.out.println(entry.getKey() + "/" + entry.getValue());
				System.out.println("key:"+entry.getKey());
				Injector injector = Guice.createInjector(new ModuleBind());
				BaseThread rapid = (BaseThread) injector.getInstance(Key
						.get(BaseThread.class, Names.named(entry.getKey())));
				rapid.postMsg(entry.getValue());
				rapid.Run();
				
			}

		}
	}
}
