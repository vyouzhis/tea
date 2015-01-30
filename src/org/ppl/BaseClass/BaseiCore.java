package org.ppl.BaseClass;

import java.util.List;
import java.util.Map;

import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;
import org.ppl.io.Encrypt;

import com.alibaba.fastjson.JSON;

public class BaseiCore extends BaseSurface {

	protected int Init() {
		String method = porg.getMehtod();
		if(method.toLowerCase().equals("get")){
			if(isLogin()==-1){
				iLogout();
				return -1;
			}
		}else{
			String username = porg.getKey("username");
			String passwd = porg.getKey("passwd_login");
			iLogin(username, passwd);
		}
		
		return 0;
	}
	
	private int isLogin(){
		int uid = igetUid();
		if(uid>0)return uid;
		return -1;
	}
	
	public int igetUid() {
		String uid = getUinfo("id");
		if(uid==null)return 0;
		return Integer.valueOf(uid);
	}
	
	public String igetName() {
		return getUinfo("alias");
	}
	
	@SuppressWarnings("unchecked")
	private String getUinfo(String key) {
		String uinfo = cookieAct.GetCookie(globale_config.Uinfo);
		
		Map<String, Object> res = JSON.parseObject(uinfo, Map.class);
		return res.get(key).toString();
	}
	
	public void iLogout() {		
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
				
		String user_salt = porg.getKey("salt");
		if(checkSalt(user_salt) == false){
			return -1;
		}
		
		String format = "SELECT * FROM `web_user` where login='%s' limit 1;";
		String sql = String.format(format, login);
		Map<String, Object> res ;
		
		res = FetchOne(sql);
		if (res==null) {
			return -1;
		}
		
		Encrypt ec = Encrypt.getInstance();
		String check_passd = ec.MD5(res.get("password").toString() + user_salt); 
		if(!check_passd.equals(pwd)) return -1;
		
		String info_json = JSON.toJSONString(res); 
		cookieAct.SetCookie(globale_config.Uinfo, info_json);
		return 0;
	}
}
