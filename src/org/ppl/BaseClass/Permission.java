package org.ppl.BaseClass;

import java.sql.SQLException;

import org.ppl.core.SystemLog;

import org.ppl.etc.UrlClassList;

import com.lib.common.Footer;
import com.lib.common.Header;
import com.lib.common.Menu;
import com.lib.common.Navbar;

public class Permission extends BaseTheme {
	private int Action = 0; // default 0 is menu, 1 is action

	protected int Init() {
		
		UrlClassList ucl = UrlClassList.getInstance();
		String bad_url = "";
		
		boolean i = aclCheckAccess();
		if (!stdClass.equals(mConfig.GetValue("login_module")) && i == false) {

			bad_url = ucl.BuildUrl("admin_login", "");

			TipMessage(bad_url,  _CLang("error_login"));
			echo("login_module error");
			return -1;
		}

		if (checkRole() == false) {
			bad_url = ucl.BuildUrl("admin_index", "");			
			TipMessage(bad_url, _CLang("error_role"));
			echo("checkRole error");
			return -1;
		}

		if (CheckOntime() == false) {
			bad_url = ucl.BuildUrl("admin_index", "");
		
			TipMessage(bad_url, _CLang("error_timeout"));
			aclLogout();
			echo("CheckOntime error");
			return -1;
		}

		Log();

		return 0;
	}

	private void Log() {
		SystemLog sl = new SystemLog();
		String sql = sl.Log(aclGetUid());
		try {
			update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void Show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void View() {
		// TODO Auto-generated method stub

		String UserName = aclGetName();

		setRoot("UserName", UserName);

		setRoot("navbar", navbar());

		setRoot("menu", menu());

		if (isAutoHtml) {
			common();
		}

		super.View();
	}

	private void common() {
		Header header = new Header();		
		header.filter();
		header_html = header.getHtml();
		
		
		Footer footer = new Footer();
		footer.filter();
		footer_html = footer.getHtml();
	
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

	public void Msg(String msg) {
		UrlClassList ucl = UrlClassList.getInstance();
		String ok_url = ucl.read("user_list");

		TipMessage(ok_url, msg);
		
	}

}
