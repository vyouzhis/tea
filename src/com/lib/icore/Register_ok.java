package com.lib.icore;

import java.sql.SQLException;
import java.util.List;

import org.ppl.BaseClass.BaseSurface;
import org.ppl.etc.globale_config;
import org.ppl.io.TimeClass;

public class Register_ok extends BaseSurface {
	private String className = null;
	public Register_ok() {
		// TODO Auto-generated constructor stub
		className = this.getClass().getCanonicalName();
		super.GetSubClassName(className);
	}
	
	@Override
	public void Show() {
		// TODO Auto-generated method stub
		List<String> rmc = porg.getRmc();
		String salt = rmc.get(1);
		String csalt = cookieAct.GetCookie(globale_config.CookieSalt);
		//if(!salt.equals(csalt)){
			echo(salt+"__"+csalt);
		//}
		
		AddUser(porg.getKey("login"), porg.getKey("passwd1"));
		super.View();
	}
	
	private void AddUser(String login, String passwd) {
		TimeClass tc = TimeClass.getInstance();
		int now = (int)tc.time();
		String format = "INSERT INTO `tea`.`web_user`" +
				" (`login`, `password`, `alias`, `email`, `ctime`) VALUES " +
				"( '%s', '%s', '%s', '%s',  '%d');";
		
		String sql = String.format(format, login, passwd, login,login, now);
		
		try {
			update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
