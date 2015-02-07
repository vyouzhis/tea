package org.ppl.Module;

import org.ppl.BaseClass.BaseModule;
import org.ppl.BaseClass.BasePrograma;
import org.ppl.BaseClass.BaseSurface;
import org.ppl.BaseClass.BaseThread;
import org.ppl.BaseClass.BaseiCore;
import org.ppl.BaseClass.LibLang;
import org.ppl.BaseClass.Permission;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;

public class ModuleBind extends ModuleBindClass implements Module {

	@SuppressWarnings("unchecked")
	@Override
	public void configure(Binder binder) {
		// TODO Auto-generated method stub
		String name = null;
		for (int i = 0; i < LangList.size(); i++) {

			Class<? extends LibLang> c = (Class<? extends LibLang>) LangList
					.get(i);
			name = SliceName(c.getName());

			binder.bind(LibLang.class).annotatedWith(Names.named(name)).to(c);
		}

		for (int i = 0; i < SurfaceList.size(); i++) {
			Class<? extends BaseSurface> bc = (Class<? extends BaseSurface>) SurfaceList
					.get(i);
			name = SliceName(bc.getName());
			binder.bind(BaseSurface.class).annotatedWith(Names.named(name))
					.to(bc);
		}
		
		for (int i = 0; i < iCoreList.size(); i++) {
			Class<? extends BaseiCore> bc = (Class<? extends BaseiCore>) iCoreList
					.get(i);
			name = SliceName(bc.getName());
			binder.bind(BaseiCore.class).annotatedWith(Names.named(name))
					.to(bc);
		}

		for (int i = 0; i < ModuleList.size(); i++) {
			Class<? extends BaseModule> mc = (Class<? extends BaseModule>) ModuleList
					.get(i);
			name = SliceName(mc.getName());

			binder.bind(BaseModule.class).annotatedWith(Names.named(name))
					.to(mc);
		}
		
		for (int i = 0; i < PermList.size(); i++) {
			Class<? extends Permission> mc = (Class<? extends Permission>) PermList
					.get(i);
			name = SliceName(mc.getName());

			binder.bind(Permission.class).annotatedWith(Names.named(name))
					.to(mc);
		}
		
		for (int i = 0; i < ManagerList.size(); i++) {
			Class<? extends BasePrograma> mc = (Class<? extends BasePrograma>) ManagerList
					.get(i);
			name = SliceName(mc.getName());

			binder.bind(BasePrograma.class).annotatedWith(Names.named(name))
					.to(mc);
		}
		
		for (int i = 0; i < ThreadList.size(); i++) {
			Class<? extends BaseThread> mc = (Class<? extends BaseThread>) ThreadList
					.get(i);
			name = SliceName(mc.getName());

			binder.bind(BaseThread.class).annotatedWith(Names.named(name))
					.to(mc);
		}

	}

}
