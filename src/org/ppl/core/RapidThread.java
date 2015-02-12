package org.ppl.core;

import org.ppl.BaseClass.BaseThread;
import org.ppl.BaseClass.LibThread;
import org.ppl.Module.ModuleBind;
import org.ppl.db.DBManager;
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
					globale_config.RapidListQueue.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			for (String key : globale_config.RapidListQueue.keySet()) {

				Injector injector = Guice.createInjector(new ModuleBind());
				BaseThread rapid = (BaseThread) injector.getInstance(Key.get(
						BaseThread.class, Names.named(key)));
				
				while(globale_config.RapidListQueue.get(key).size()>0) {
					
					Object o = globale_config.RapidListQueue.get(key).pop();					
					rapid.mailbox(o);
					rapid.Run();					
				}				
				rapid.free();
			}
		}
	}
}
