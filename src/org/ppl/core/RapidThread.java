package org.ppl.core;

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

		ListQueue();
	}

	private void ListQueue() {
		while (true) {
			try {
				globale_config.RapidListQueue.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (Map.Entry<String, Object> entry : globale_config.RapidListQueue.entrySet()) {
				//System.out.println(entry.getKey() + "/" + entry.getValue());
				System.out.println(entry.getKey());
				Injector injector = Guice.createInjector(new ModuleBind());
				BaseThread rapid = (BaseThread) injector.getInstance(Key
						.get(BaseThread.class, Names.named(entry.getKey())));
				rapid.Run();
				
			}

		}
	}
}
