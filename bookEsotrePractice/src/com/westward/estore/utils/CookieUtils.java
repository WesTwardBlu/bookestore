package com.westward.estore.utils;

import javax.servlet.http.Cookie;

/**
 * Cookie的工具类
 * */
public class CookieUtils {
	private CookieUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public static Cookie findCookieByName(Cookie[] cookies,String name){
		if (cookies== null || cookies.length==0) {
			return null;
		}
		
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie;
			}
		}
		
		return null;
	}
}
