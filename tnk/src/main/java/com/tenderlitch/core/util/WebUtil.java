/**
 * 
 */
package com.tenderlitch.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tenderliTch
 *
 */
public class WebUtil {
	/**
	 * 判断请求是否是ajax请求
	 * @param request
	 * @return
	 */
	public static final boolean isRequestAjax(HttpServletRequest request){
		return request!=null && request.getHeader(AJAX_HEADER)!=null;
	}
	
	private static final String AJAX_HEADER="X-Requested-With";
}
