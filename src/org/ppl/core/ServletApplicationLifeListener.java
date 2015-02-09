package org.ppl.core;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.ppl.BaseClass.BaseThread;
import org.ppl.BaseClass.LibLang;
import org.ppl.Module.ModuleBind;
import org.ppl.db.HikariConnectionPool;
import org.ppl.etc.Config;
import org.ppl.etc.globale_config;

import com.alibaba.fastjson.JSON;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

@WebListener
public class ServletApplicationLifeListener extends PObject implements
		ServletContextListener {
	private Config mConfig;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		HikariConnectionPool.getInstance();
		InitPackList();
		mConfig = new Config(globale_config.Config);		
		int autorun = mConfig.GetInt("autorun");
//		if (autorun == 1) {
//			Auto();
//		}
	}

	@SuppressWarnings("unchecked")
	private void Auto() {
		String runJson = mConfig.GetValue("rum.module");
		List<String> runList = JSON.parseObject(runJson, List.class);
		Injector injector = Guice.createInjector(new ModuleBind());
		for (String rl : runList) {
			BaseThread libLan = (BaseThread) injector.getInstance(Key.get(
					BaseThread.class, Names.named(rl)));
			libLan.Run();
		}

	}
	
	@SuppressWarnings("unchecked")
	private void InitPackList() {
		Config mConfig = new Config(globale_config.Config);
		String packs = mConfig.GetValue("base.packs");
		List<String> pList = JSON.parseObject(packs, List.class);
		for (String p : pList) {
			String ps = this.getClass().getResource("/").getPath() + p;			
			findPack(ps);
		}

	}

}
