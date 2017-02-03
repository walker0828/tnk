/**
 * 
 */
package com.tenderlitch.core.web;

/**
 * ajax请求相应中的状态
 * @author tenderliTch
 *
 */
public enum ResponseStatus {
	/**
	 * 未登录状态
	 */
	NOT_LOGON(0),
	/**
	 * 请求成功
	 */
	SUCCESS(1),
	/**
	 * 处理失败
	 */
	FAILURE(2);
	
	public int getStatus() {
		return status;
	}
	
	private ResponseStatus(int status){
		this.status=status;
	}
	
	private int status;
}
