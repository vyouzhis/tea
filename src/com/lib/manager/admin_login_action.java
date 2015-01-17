package com.lib.manager;

import java.util.Map;

import org.ppl.BaseClass.Permission;
import org.ppl.common.PorG;
import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;

public class admin_login_action extends Permission {

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		
		PorG pg = PorG.getInstance();
		String action = pg.porg("action");
		if(action!=null){
			login();
		}else {
			LogOut();
		}
	}
	
	private void login() {
		PorG pg = PorG.getInstance();
		String salt = pg.porg("salt");
		String email = pg.porg("email");
		String passwd = pg.porg("passwd");
				
		UrlClassList ucl = UrlClassList.getInstance();
		String bad_url = ucl.BuildUrl("admin_login", "");
		String ok_url = ucl.BuildUrl("admin_index", "");
		
		if(passwd == null || salt == null || email == null){
			Bad(bad_url,super._CLang("error_passwd"));
			return;
		}
		
		boolean isSalt = checkSalt(salt);
		if(isSalt==false){
			Bad(bad_url,super._CLang("error_salt"));
			return;
		}
		
		int i = aclLogin(email, passwd, salt);
		
		switch (i) {
		case 0:
			Bad(ok_url,super._CLang("welcome"));
			return;			
		case -1:			
			Bad(bad_url,super._CLang("error_notexist"));
			return;
		case -2:			
			Bad(bad_url,super._CLang("error_passwd"));
			return;
		default:
			Bad(bad_url,super._CLang("error_nothing"));
			return;
		}
	}
	
	private void LogOut() {
		aclLogout();
		UrlClassList ucl = UrlClassList.getInstance();
		String bad_url = ucl.BuildUrl("admin_login", "");
		Bad(bad_url, super._CLang("bye"));
		return;
	}
	
	private void Bad(String url, String msg) {
		isAutoHtml = false;
		ShowMessage ms = ShowMessage.getInstance();				
		String res = ms.SetMsg(url, msg, 3000);
		
		super.setHtml(res);
	}
}
