package com.tenderlitch.core.web;

import javax.servlet.http.HttpSession;

public class LoginUtil {

	public static void save2Session(HttpSession session , String userId) {
		session.setAttribute(USER_KEY, userId);
	}
	
	public static boolean isLogined(HttpSession session){
		return session.getAttribute(USER_KEY)!=null;
	}

	public static String getUpcUserId(HttpSession session){
		return (String)session.getAttribute(USER_KEY);
	}
	
	/**
	 * 销毁
	 */
	public static void destroy(HttpSession session) {
		session.removeAttribute(USER_KEY);
	}
	
	private static final String USER_KEY="tenderlitch-userkey";
}
