package com.lib.manager;

import org.ppl.BaseClass.Permission;
import org.ppl.common.PorG;
import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;

public class admin_login_action extends Permission {

	@Override
	public void Show() {
		// TODO Auto-generated method stub
		isAutoHtml = false;
		String action = porg.getKey("action");
		if(action!=null){
			login();
		}else {
			LogOut();
		}
	}
	
	private void login() {
		
		String salt = porg.getKey("salt");
		String email = porg.getKey("email");
		String passwd = porg.getKey("passwd");
				
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
		
		ShowMessage ms = ShowMessage.getInstance();				
		String res = ms.SetMsg(url, msg, 3000);
		
		super.setHtml(res);
	}
}
