package org.ppl.Module;

import java.util.ArrayList;
import java.util.List;

import org.ppl.BaseClass.BaseModule;
import org.ppl.BaseClass.BasePrograma;
import org.ppl.BaseClass.BaseSurface;
import org.ppl.BaseClass.BaseThread;
import org.ppl.BaseClass.BaseiCore;
import org.ppl.BaseClass.LibLang;
import org.ppl.BaseClass.Permission;
import org.ppl.etc.UrlClassList;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;

public class ModuleBind extends ModuleBindClass implements Module {

	private Binder binder;

	@SuppressWarnings("unchecked")
	@Override
	public void configure(Binder binder) {
		// TODO Auto-generated method stub	
		this.binder = binder;
		for (int i = 0; i < LangList.size(); i++) {

			Add(LibLang.class, (Class<? extends LibLang>) LangList.get(i));
		}

		

		for (int i = 0; i < iCoreList.size(); i++) {

			Add(BaseiCore.class, (Class<? extends BaseiCore>) iCoreList.get(i));
		}

		for (int i = 0; i < ModuleList.size(); i++) {

			Add(BaseModule.class,
					(Class<? extends BaseModule>) ModuleList.get(i));
		}

		for (int i = 0; i < PermList.size(); i++) {

			Add(Permission.class, (Class<? extends Permission>) PermList.get(i));
		}

		for (int i = 0; i < ManagerList.size(); i++) {

			Add(BasePrograma.class,
					(Class<? extends BasePrograma>) ManagerList.get(i));
		}

		for (int i = 0; i < ThreadList.size(); i++) {

			Add(BaseThread.class,
					(Class<? extends BaseThread>) ThreadList.get(i));
		}
		
		for (int i = 0; i < SurfaceList.size(); i++) {

			Add(BaseSurface.class,
					(Class<? extends BaseSurface>) SurfaceList.get(i));
		}
		List<String> surface = new ArrayList<>();
		surface.add("Register");
		surface.add("home");
		surface.add("Register_ok");
		//surface.add("icore_login");
		surface.add("article");
				
		for (String sf: surface) {
			try {
				Class<? extends BaseSurface> cls = (Class<? extends BaseSurface>) Class.forName("com.lib.surface."+sf);
				Add(BaseSurface.class, cls);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	public <T> void Add(@SuppressWarnings("rawtypes") Class mClass, Class<T> clazz) {		
		String name = SliceName(clazz.getName());
		binder.bind(mClass).annotatedWith(Names.named(name)).to(clazz);
	}

}
