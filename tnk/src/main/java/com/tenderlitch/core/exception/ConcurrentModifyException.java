package com.tenderlitch.core.exception;

/**
 * ---------------------------------------------------------------------------------
 Confidential and Proprietary                                                                
 Copyright 2008 By                                                                                     
 SGAI & Hewlett-Packard Development Company, L.P. 	              
 All Rights Reserved                                                                                  

Project Name : SGAI  MES                                                                                                                                       
 Class Name   : ConcurrentModifyException.java    
 Package      : com.hp.common.exception                                                                   
 $Id: ConcurrentModifyException.java 2475 2008-07-09 03:51:13Z xia.li2 $       :                                                                  
 版本控制并发修改异常
 */
public class ConcurrentModifyException extends BaseRuntimeException{
	
	public ConcurrentModifyException(){
		super();
	}
	
	private static final long serialVersionUID = -1019650757910180626L;
}
