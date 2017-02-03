/**
 * 
 */
package com.tenderlitch.core.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 处理前台分页参数的组件
 * @author tenderliTch
 *
 */
public class PageRequest {
	
	/**
	 * 从HttpServletRequest中整理分页参数
	 * @param request
	 * @return
	 */
	public static PageBounds preparePageBound(HttpServletRequest request) {
		String paramOffset=request.getParameter(PARAM_OFFSET),
				paramLimit=request.getParameter(PARAM_LIMIT);
		
		int offset=StringUtils.isNotBlank(paramOffset)?(Integer.valueOf(paramOffset)+1):DEFAULT_PAGE_OFFSET,
				limit=StringUtils.isNotBlank(paramLimit)?Integer.valueOf(paramLimit):DEFAULT_PAGE_LIMIT;
		
		PageBounds pageBounds = new PageBounds(offset, limit);
		return pageBounds;
	}
	
	/**
	 * 默认的起始页码
	 */
	private static final int DEFAULT_PAGE_OFFSET = 0;
	/**
	 * 默认的每页行数
	 */
	private static final int DEFAULT_PAGE_LIMIT=5;
	
	/**
	 * 第几页在requestMap中的key
	 */
	private static final String PARAM_OFFSET="iDisplayStart";
	
	/**
	 * 第几页在requestMap中的key
	 */
	private static final String PARAM_LIMIT="iDisplayLength";
}
