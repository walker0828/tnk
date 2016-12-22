package com.tenderlitch.core.exception;

/**
 * ---------------------------------------------------------------------------------
 Confidential and Proprietary                                                                
 Copyright 2008 By                                                                                     
 SGAI & Hewlett-Packard Development Company, L.P. 	              
 All Rights Reserved                                                                                  

 Project Name : SGAI  MES                                                                                                                                       
 Class Name   : MesBizException.java    
 Package      : com.hp.common.exception                                                                   
 @version     $Id: MesBizException.java 7824 2008-08-18 09:13:49Z xia.li2 $                                                          
 @author xiali2
 @since  2008-8-8 
 
 */
public interface BizException {
	public String getCode();

	public String[] getParams();
}
