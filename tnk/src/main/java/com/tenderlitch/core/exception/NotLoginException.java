package com.tenderlitch.core.exception;


/**
 * ---------------------------------------------------------------------------------
 Confidential and Proprietary                                                                
 Copyright 2008 By                                                                                     
 SGAI & Hewlett-Packard Development Company, L.P. 	              
 All Rights Reserved                                                                                  

 Project Name : SGAI  MES                                                                                                                                       
 Class Name   : NotLoginException.java    
 Package      : com.hp.common.exception                                                                   
 @version     $Id: NotLoginException.java 21970 2008-12-11 02:05:10Z xia.li2 $                                                          
 @author xiali2
 @since  2008-12-11 
 */
public class NotLoginException extends BaseRuntimeException {

    private static final long serialVersionUID = -927028338979047567L;

    public NotLoginException() {
        super("global.error.code.not.login", new String[] {});
    }
}
