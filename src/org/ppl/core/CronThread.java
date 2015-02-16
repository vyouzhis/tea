package org.ppl.core;

import java.util.List;

import org.ppl.BaseClass.BaseCronThread;
import org.ppl.BaseClass.LibThread;
import org.ppl.Module.ModuleBind;
import org.ppl.etc.UrlClassList;
import org.ppl.io.TimeClass;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

public class CronThread extends LibThread {
		
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		ListQueue();
	}

	@Override
	public void ListQueue() {
		// TODO Auto-generated method stub
		UrlClassList ucl = UrlClassList.getInstance();
		TimeClass tc = TimeClass.getInstance();
		
		
		while (true) {
			try {
				Thread.sleep(60 * 5 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String min = tc.TimeStamptoDate(tc.time(), "mm");
			String hou = tc.TimeStamptoDate(tc.time(), "hh");
			String dy = tc.TimeStamptoDate(tc.time(), "dd");
			System.out.println("day:" + dy + " hour:" + hou + " min:" + min);
			
			for (String ps : ucl.getPackList()) {
				try {
					Class<?> clazz = Class.forName(ps);

					if (clazz.getSuperclass().equals(BaseCronThread.class)) {
						String name = SliceName(ps);
						Injector injector = Guice
								.createInjector(new ModuleBind());
						BaseCronThread cron = (BaseCronThread) injector
								.getInstance(Key.get(BaseCronThread.class,
										Names.named(name)));
						int minu = cron.minute();
						int hour = cron.hour();
						int day = cron.day();

						cron.Run();
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private String SliceName(String k) {
		String[] name = k.split("\\.");
		String cName = name[name.length - 1];
		return cName;
	}

}
