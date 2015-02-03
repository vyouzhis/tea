package org.ppl.BaseClass;

import java.util.Map;

import org.ppl.etc.globale_config;
import org.ppl.io.Encrypt;

import com.alibaba.fastjson.JSON;
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
