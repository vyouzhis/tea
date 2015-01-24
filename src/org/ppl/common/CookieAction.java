package org.ppl.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieAction {
	private HttpServletRequest request;
	private static CookieAction source;

	public static CookieAction getInstance() {
		if (source == null) {
			source = new CookieAction();
		}

		return source;
	}

	public void init(HttpServletRequest req) {
		request = req;
	}

	public void SetCookie(String key, String val) {
		request.setAttribute(key, val);
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
