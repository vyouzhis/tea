package org.ppl.BaseClass;

import com.lib.common.SurfaceMenu;

public class BaseSurface extends BaseTheme {

	@Override
	public void Show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void View() {
		// TODO Auto-generated method stub

		setRoot("menu", menu());
		super.View();
	}

	private String menu() {
		SurfaceMenu sMenu = new SurfaceMenu();
		sMenu.filter();
		return sMenu.getHtml();
	}	
	
}
