package com.tenderlitch.core.web;

import javax.servlet.http.HttpSession;

import com.tenderlitch.entity.upc.UpcUser;

public class LoginUtil {

	/**
	 * 保存当前登陆用户信息到session中
	 * @param session
	 * @param user
	 */
	public static void save2Session(HttpSession session , UpcUser user) {
		session.setAttribute(USER_KEY, user);
	}
	
	/**
	 * 判断当前会话是否已经登陆
	 * @param session
	 * @return
	 */
	public static boolean isLogined(HttpSession session){
		return session.getAttribute(USER_KEY)!=null;
	}

	/**
	 * 获得当前会话的登陆用户
	 * @param session
	 * @return
	 */
	public static UpcUser getCurrentUser(HttpSession session){
		return (UpcUser)session.getAttribute(USER_KEY);
	}
	
	/**
	 * 销毁
	 */
	public static void destroy(HttpSession session) {
		session.removeAttribute(USER_KEY);
	}
	
	/**
	 * 登陆用户信息放在session中时使用的key
	 */
	private static final String USER_KEY="tenderlitch-userkey";
}
