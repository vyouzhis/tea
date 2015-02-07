package org.ppl.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.ppl.BaseClass.LibLang;
import org.ppl.Module.ModuleBind;
import org.ppl.db.HikariConnectionPool;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

@WebListener
public class ServletApplicationLifeListener extends PObject implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub		
		HikariConnectionPool.getInstance();
		  
		
		Injector injector = Guice.createInjector(new ModuleBind());
		LibLang libLan = (LibLang) injector.getInstance(Key.get(
				LibLang.class, Names.named("")));
	}

}
