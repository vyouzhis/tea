package org.ppl.etc;

import java.util.ArrayList;
import java.util.List;

import org.ppl.common.PorG;

public class UrlClassList {
	static UrlClassList ucl = null;
	private List<String> ucls = null;

	public static UrlClassList getInstance() {
		if (ucl == null) {
			ucl = new UrlClassList();
		}

		return ucl;
	}

	public List<String> getUcls() {
		return ucls;
	}

	public void setUcls(String libName) {
		if (this.ucls == null) {
			this.ucls = new ArrayList<String>();
		}
		if (!this.ucls.contains(libName))
			this.ucls.add(libName);
	}

	public String BuildUrl(String libName, String action) {
		PorG pg = PorG.getInstance();
		String url = pg.getContext_Path() + "/" + libName + "/" + action;
		return url;
	}

	public String read(String libName) {
		return BuildUrl(libName, "read");
	}

	public String create(String libName) {
		return BuildUrl(libName, "create");
	}

	public String edit(String libName) {
		return BuildUrl(libName, "edit");
	}

	public String remove(String libName) {
		return BuildUrl(libName, "remove");
	}

	public String search(String libName) {
		return BuildUrl(libName, "search");
	}

}
