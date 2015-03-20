package org.ppl.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.ppl.BaseClass.BaseRapidThread;
import org.ppl.BaseClass.LibThread;
import org.ppl.etc.globale_config;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class RapidThread extends LibThread {
	
	/**
	 * @since rapid thread run
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("rapidthread run");
		ListQueue();
	}

	/**
	 * @since rapid thread list module queue
	 */
	public void ListQueue() {
		Map<String, LinkedList<Object>> threadList = new HashMap<String, LinkedList<Object>>();

		while (true) {
			synchronized (globale_config.RapidListQueue) {
				try {
					globale_config.RapidListQueue.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//System.out.println("+===gl size:"+globale_config.RapidListQueue.size());
				for (String key : globale_config.RapidListQueue.keySet()) {
					threadList.put(key, globale_config.RapidListQueue.get(key));
				}
				globale_config.RapidListQueue.clear();
			}

			//System.out.println("threaList size:" + threadList.size());
			for (String key : threadList.keySet()) {

				Injector injector = globale_config.injector;
				BaseRapidThread rapid = (BaseRapidThread) injector
						.getInstance(Key.get(BaseRapidThread.class,
								Names.named(key)));
				System.out.println("size:" + threadList.get(key).size());
				while (threadList.get(key).size() > 0) {
					Object o = threadList.get(key).pop();					
					rapid.mailbox(o);
					rapid.Run();
				}
				rapid.free();
			}
			threadList.clear();
			//System.out.println("list Queue end size:" + threadList.size());

		}
	}
}
