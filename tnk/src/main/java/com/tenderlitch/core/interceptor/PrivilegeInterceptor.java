package com.tenderlitch.core.interceptor;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tenderlitch.core.exception.NotLoginException;
import com.tenderlitch.core.exception.ResourceNotAvailableException;
import com.tenderlitch.core.util.WebUtil;
import com.tenderlitch.core.web.LoginUtil;
import com.tenderlitch.entity.upc.UpcUser;
import com.tenderlitch.service.upc.UpcUrlService;
import com.tenderlitch.service.upc.UpcUserService;

public class PrivilegeInterceptor extends HandlerInterceptorAdapter {

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
		UpcUser user=LoginUtil.getCurrentUser(request.getSession());
		if (ACTION_URL_LOGIN.equals(url)) {
			return super.preHandle(request, response, handler);
		}else if(user!=null){
			Set<String> availableUrls=upcUserService.getUserAvailableUrls(user.getSid());
			//白名单模式,只有被列为有权限访问的资源才允许通过
			if(availableUrls!=null && availableUrls.contains(url)){
				//有权限访问资源
				return super.preHandle(request, response, handler);
			}else{
				//如果是ajax请求,返回约定好的JSON串
				if(WebUtil.isRequestAjax(request)){
					String resourceName=upcUrlService.findUrlResourceByUrl(url);
					resourceName=resourceName!=null?resourceName:url;//如果访问的资源未配置权限,用URL代替,给开发人员使用
					throw new ResourceNotAvailableException(resourceName);//交由统一的异常处理器来处理响应
				}
				//如果是提交表单的请求,显示404页面
				else{
					String contextPath = request.getContextPath();
					response.sendRedirect(contextPath + PAGE_URL_404);
				}
				return false;
			}
		}else{
			//如果是ajax请求,返回约定好的JSON串
			if(WebUtil.isRequestAjax(request)){
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
	
	/**
	 * 几个固有页面的访问地址
	 */
	private static final String PAGE_URL_404="/404.html",
			PAGE_URL_LOGIN="/login.html",
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
	 * 用户权限业务接口
	 */
	@Resource
	private UpcUserService upcUserService;
	
	/**
	 * 资源权限业务接口
	 */
	@Resource
	private UpcUrlService upcUrlService;

}
