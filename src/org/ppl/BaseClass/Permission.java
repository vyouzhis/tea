package org.ppl.BaseClass;

import org.ppl.common.ShowMessage;
import org.ppl.etc.Config;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;

import com.lib.common.Menu;
import com.lib.common.Navbar;

public class Permission extends BaseTheme  {
	private int Action=0; // default 0 is menu, 1 is action
	protected int Init() {
		Config mConfig = new Config(globale_config.Config);
		int uid = aclGetUid();
		UrlClassList ucl = UrlClassList.getInstance();
		String bad_url = "";
		ShowMessage ms = ShowMessage.getInstance();
		String res = "";
		if (uid == 0 && !stdClass.equals(mConfig.GetValue("login_module"))) {

			bad_url = ucl.BuildUrl("admin_login", "");

			res = ms.SetMsg(bad_url, _CLang("error_login"), 3000);

			super.setHtml(res);
			return -1;
		}
		if (checkRole() == false) {
			bad_url = ucl.BuildUrl("admin_index", "");
			res = ms.SetMsg(bad_url, _CLang("error_role"), 3000);

			super.setHtml(res);
			return -1;
		}
		return 0;
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void View() {
		// TODO Auto-generated method stub
		setRoot("static_uri", porg.getContext_Path());

		String UserName = aclgetName();
		
		setRoot("UserName", UserName);

		setRoot("navbar", navbar());

		setRoot("menu", menu());
		
		super.View();
	}

	public String GetName() {
		return _MLang(stdClass);
	}

	public String GetDesc() {
		return _MLang(stdClass + "_desc");
	}

	public String navbar() {
		Navbar nb = new Navbar();
		nb.filter();
		String html = nb.getHtml();

		return html;
	}

	public String menu() {
		Menu m = new Menu();
		m.filter();
		String html = m.getHtml();

		return html;
	}

	public int getAction() {
		return Action;
	}

	public void setAction(int action) {
		Action = action;
	}
}
