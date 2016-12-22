package com.tenderlitch.core.exception;

/**
 * ---------------------------------------------------------------------------------
 Confidential and Proprietary                                                                
 Copyright 2008 By                                                                                     
 SGAI & Hewlett-Packard Development Company, L.P. 	              
 All Rights Reserved                                                                                  
 
 Project Name : SGAI  MES                                                                                                                                       
 Class Name   : AbsentParamtersException.java    
 Package      : com.hp.common.exception                                                                   
 @version     $Id: AbsentParamtersException.java 7750 2008-08-18 04:34:24Z xia.li2 $                                                          
 @author xiali2
 @since  2008-8-8 
 缺少程序运行的关键参数错误
 */
public class AbsentParamtersException extends BaseRuntimeException{

	/**
	 * 
	 * @param param 参数名
	 */
	public AbsentParamtersException(String param){
		super("global.error.code.absent.paramters",new String[]{param});
	}
	
	private static final long serialVersionUID = 8666181320643237036L;
}
