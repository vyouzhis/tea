package org.ppl.BaseClass;

import java.util.Map;

import org.ppl.common.ShowMessage;
import org.ppl.etc.UrlClassList;
import org.ppl.etc.globale_config;
import org.ppl.io.DesEncrypter;
import org.ppl.io.Encrypt;

import com.alibaba.fastjson.JSON;

public class BaseiCore extends BaseSurface {

	protected int Init() {
		String method = porg.getMehtod();

		if (method.toLowerCase().equals("get")) {
			if (isLogin() == -1) {
				iLogout();

				return -1;
			}
		} else {
			String username = porg.getKey("username");
			String passwd = porg.getKey("passwd_login");
			if (iLogin(username, passwd) == -1) {

				iLogout();
				return -1;
			}
		}

		return 0;
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

		cookieAct.DelCookie(globale_config.Uinfo);
	}

	public int iLogin(String login, String pwd) {
		if (login.length() < 1)
			return -1;
		if (pwd.length() < 1)
			return -1;

		String user_salt = porg.getKey("salt");
		if (checkSalt(user_salt) == false) {
			return -1;
		}

		String format = "SELECT * FROM `web_user` where login='%s' limit 1;";
		String sql = String.format(format, login);

		Map<String, Object> res;

		res = FetchOne(sql);
		if (res == null) {
			return -1;
		}

		Encrypt ec = Encrypt.getInstance();
		String check_passd = ec.MD5(res.get("password").toString() + user_salt);

		if (!check_passd.equals(pwd))
			return -1;

		String info_json = JSON.toJSONString(res);

		String hex = ec.toHex(info_json);

		cookieAct.SetCookie(globale_config.Uinfo, hex);
		return 0;
	}
}
