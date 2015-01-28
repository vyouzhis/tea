package org.ppl.BaseClass;

import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;

public class BaseiCore extends BaseSurface {

	protected int Init() {
		if(isLogin()==-1){
			Logout();
			return -1;
		}
		return 0;
	}
	
	private int isLogin(){
		
		return -1;
	}
	
	public int igetUid() {
		String uid = cookieAct.GetCookie("id");
		if(uid==null)return 0;
		return Integer.valueOf(uid);
	}
	
	public void Logout() {		
		UrlClassList ucl = UrlClassList.getInstance();
		String bad_url = "";
		ShowMessage ms = ShowMessage.getInstance();
		String res = "";
		bad_url = ucl.BuildUrl("login", "");

		res = ms.SetMsg(bad_url, _CLang("error_login"), 3000);
		isAutoHtml = false;
		super.setHtml(res);
		
		cookieAct.SetCookie("", "", 1);
	}
	
	public int iLogin(String login, String pwd) {
		if(login.length()<1)return -1;
		if(pwd.length()<1)return -1;
		
		return -1;
	}
}
