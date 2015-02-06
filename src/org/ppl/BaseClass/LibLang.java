package org.ppl.BaseClass;

import org.ppl.core.PObject;
import org.ppl.etc.Config;
import org.ppl.etc.globale_config;

public class LibLang extends PObject implements BaseLangInterface {
	public Config CLang = null;
	private String selfPath = "";

	public LibLang() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String _Lang(String key) {
		// TODO Auto-generated method stub
		LangConfig();
		return CLang.GetValue(key);
	}

	public void LangConfig() {
		String LibName = SliceName(stdClass);
		Config mConfig = new Config(globale_config.Config);

		String path = "properties/lang/" + mConfig.GetValue("Languages")
				+ selfPath + "/" + LibName.substring(1) + ".properties";

		CLang = new Config(path);
	}

	public void SelfPath(String path) {
		String[] p = path.split("\\.");
		for (int i = 2; i < p.length; i++) {
			selfPath += "/" + p[i];
		}

	}
}
