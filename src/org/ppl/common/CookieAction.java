package org.ppl.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieAction {
	private HttpServletRequest request;
	HttpServletResponse response;
	private static CookieAction source;

	public static CookieAction getInstance() {
		if (source == null) {
			source = new CookieAction();					
		}

		return source;
	}

	public void init(HttpServletRequest req,HttpServletResponse res) {
		request = req;
		response = res;			
	}

	public void SetCookie(String key, String val) {		
		Cookie userCookie = new Cookie(key, val);
		userCookie.setMaxAge(60*60*24*365); //Store cookie for 1 year
		response.addCookie(userCookie);
	}
	
	public void SetCookie(String key, String val, int timeOut) {		
		Cookie userCookie = new Cookie(key, val);
		userCookie.setMaxAge(timeOut); //Store cookie for 1 year
		response.addCookie(userCookie);
	}

	public String GetCookie(String key) {
		Cookie[] cookies = request.getCookies();
						
		for(Cookie cookie : cookies){
		    if(key.equals(cookie.getName())){
		        return cookie.getValue();
		    }
		}
		
		return null;
	}
}
