package com.tenderlitch.core.exception;

/**
 * ---------------------------------------------------------------------------------
 Confidential and Proprietary                                                                
 Copyright 2008 By                                                                                     
 SGAI & Hewlett-Packard Development Company, L.P. 	              
 All Rights Reserved                                                                                  

Project Name : SGAI  MES                                                                                                                                       
 Class Name   : DaoException.java    
 Package      : com.hp.common.exception                                                                   
 $id$                                                                  
 数据层运行异常定义
 */
public class DaoException extends BaseRuntimeException {
	private static final long serialVersionUID = -5803265594743554136L;
	
	public DaoException(String code,String[] params,Exception e) {
		super(code,e);
		this.setCode(code);
		this.setParams(params);
	}
	
}
