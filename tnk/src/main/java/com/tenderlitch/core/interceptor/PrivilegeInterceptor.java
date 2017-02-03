package com.tenderlitch.core.interceptor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tenderlitch.core.exception.NotLoginException;
import com.tenderlitch.core.util.WebUtil;
import com.tenderlitch.core.web.LoginUtil;

public class PrivilegeInterceptor extends HandlerInterceptorAdapter {

	// TODO 目前这个类没有任何作用,需要决定删除还是增加能起权限控制作用的方法

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String url = request.getServletPath();// 请求的路径
		//request.getPathInfo();
		System.out.println(url);
		
		/*
		 * if("/resources/login.html".equals(url)){ return true; }else{ String
		 * contextPath=request.getContextPath();
		 * 
		 * response.sendRedirect(contextPath+"/resources/login.html"); return
		 * false; }
		 */
		//isFreeToAccessUrl(url)
		if (ACTION_URL_LOGIN.equals(url) || LoginUtil.isLogined(request.getSession())) {
			return super.preHandle(request, response, handler);
		}else{
			//如果是ajax请求,返回约定好的JSON串
			if(WebUtil.isRequestAjax(request)){
//				response.setStatus(500);
//				customObjectMapper.writeValue(response.getWriter(), AjaxResponse.notLogOn());
				throw new NotLoginException();//交由统一的异常处理器来处理响应
			}
			//如果是提交表单的请求,返回login页面
			else{
				String contextPath = request.getContextPath();
				response.sendRedirect(contextPath + PAGE_URL_LOGIN);
			}
			return false;
		}
	}
	
//	@Resource
//	private CustomObjectMapper customObjectMapper;
	
	/*private static boolean isFreeToAccessUrl(String url){
		return PUBLIC_URLS.contains(url) || isStaticResources(url);
	}*/

	/*private static boolean isStaticResources(String url) {
		return url!=null &&(
				url.endsWith(STATIC_RESOURCES_SUFIX_CSS)
				||url.endsWith(STATIC_RESOURCES_SUFIX_JS)
				||url.endsWith(STATIC_RESOURCES_SUFIX_PNG)
				||url.endsWith(STATIC_RESOURCES_SUFIX_JPG)
				||url.endsWith(STATIC_RESOURCES_SUFIX_GIF)
				||url.endsWith(STATIC_RESOURCES_SUFIX_ICO)
				);
	}*/
	
	/**
	 * 几个固有页面的访问地址
	 */
	private static final String PAGE_URL_404="/404.html",
			PAGE_URL_LOGIN="/login.html",
//			PAGE_URL_INDEX="/index.html",
			ACTION_URL_LOGIN="/login";

	/**
	 * 不用经过权限校验的请求
	 */
	private static final Set<String> PUBLIC_URLS = new HashSet<String>();

	static {
		PUBLIC_URLS.add(PAGE_URL_404);
		PUBLIC_URLS.add(PAGE_URL_LOGIN);
	}

	/**
	 * 不用校验权限的静态资源,url以xx结尾
	 */
	/*private static final String STATIC_RESOURCES_SUFIX_JS = ".js",
			STATIC_RESOURCES_SUFIX_CSS = ".css",
			STATIC_RESOURCES_SUFIX_PNG = ".png",
			STATIC_RESOURCES_SUFIX_JPG = ".jpg",
			STATIC_RESOURCES_SUFIX_GIF = ".gif",
			STATIC_RESOURCES_SUFIX_ICO=".ico";*/

}
