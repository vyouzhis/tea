package com.lang.surface;

import org.ppl.BaseClass.LibLang;

public class _SurfaceMenu extends LibLang {

	public _SurfaceMenu() {
		// TODO Auto-generated constructor stub
		String className = this.getClass().getCanonicalName();
		GetSubClassName(className);
		SelfPath(this.getClass().getPackage().getName());
	}
}
