package com.tenderlitch.core.exception;

import org.apache.commons.lang3.StringUtils;

import com.tenderlitch.core.service.AppServiceHelper;

/**
 * ---------------------------------------------------------------------------------
 Confidential and Proprietary                                                                
 Copyright 2008 By                                                                                     
 SGAI & Hewlett-Packard Development Company, L.P. 	              
 All Rights Reserved                                                                                  

Project Name : SGAI  MES                                                                                                                                       
 Class Name   : BaseRuntimeException.java    
 Package      : com.hp.common.exception                                                                   
 $Id: BaseRuntimeException.java 43985 2012-01-06 06:03:27Z xiangju.duan $       :                                                                  
 运行时类型异常基础类
 */
public class BaseRuntimeException extends RuntimeException implements
		BizException {
	private static final long serialVersionUID = 5806782400259918073L;

	private String code;

	private String[] params;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}

	public BaseRuntimeException() {
		super();
	}

	public BaseRuntimeException(String message, Exception e) {
		super(message, e);
	}

	public BaseRuntimeException(String message) {
		super(message);
	}

	public BaseRuntimeException(Exception e) {
		super(e);
	}

	public BaseRuntimeException(String code, String[] params) {
		super(code);
		this.setCode(code);
		this.setParams(params);
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		if (code == null || code.length() == 0) {
			return super.getMessage();
		}
		
		java.util.Locale locale=null;

		String i18n = AppServiceHelper.getMessage(code,params, locale);

		if (StringUtils.isNotEmpty(i18n)) {
			return i18n;
		} 
		
		String codeMessage = "操作失败!异常代码[" + code + "]未设置国际化信息.";
		return codeMessage;
	}

	public String toString() {
		String s = getClass().getName();
		String message = this.getMessage();
		return (message != null) ? (s + ": " + message) : s;
	}

	

	
	
}
