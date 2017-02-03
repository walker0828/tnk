/**
 * Ajax请求的响应格式
 */
package com.tenderlitch.core.web;

import java.util.List;

/**
 * @author tenderliTch
 *
 */
public class AjaxResponse {
	
	/**
	 * 处理成功,没有响应数据和消息
	 * @return
	 */
	public static AjaxResponse success(){
		return new AjaxResponse(ResponseStatus.SUCCESS);
	}
	
	/**
	 * 处理成功,附带消息
	 * @return
	 */
	public static AjaxResponse success(String msg){
		return new AjaxResponse(ResponseStatus.SUCCESS,msg);
	}
	
	/**
	 * 分页请求处理成功,返回数据
	 * @return
	 */
	public static AjaxResponse success(com.github.pagehelper.Page<?> data){
		return new AjaxResponse(ResponseStatus.SUCCESS, data);
	}
	
	/**
	 * 非分页请求处理成功,返回数据
	 * @return
	 */
	public static AjaxResponse success(List<?> data){
		return new AjaxResponse(ResponseStatus.SUCCESS, data);
	}
	
	/**
	 * 处理失败,附加消息
	 * @return
	 */
	public static AjaxResponse failure(String msg){
		return new AjaxResponse(ResponseStatus.FAILURE, msg);
	}
	
	/**
	 * 未登陆状态
	 * @return
	 */
	public static AjaxResponse notLogOn(){
		return new AjaxResponse(ResponseStatus.NOT_LOGON);
	}
	
	/**
	 * 只能从工厂中获得实例
	 */
	private AjaxResponse(){}
	
	/**
	 * 只能从工厂中获得实例
	 */
	private AjaxResponse(ResponseStatus status, com.github.pagehelper.Page<?> data){
		this.status=status.getStatus();
		this.aaData=data.getResult();
		this.iTotalRecords=data.getTotal();
		this.iTotalDisplayRecords=iTotalRecords.intValue();
	}
	
	/**
	 * 只能从工厂中获得实例
	 */
	private AjaxResponse(ResponseStatus status, List<?> data){
		this.status=status.getStatus();
		this.aaData=data;
	}
	
	/**
	 * 只能从工厂中获得实例
	 */
	private AjaxResponse(ResponseStatus status, String msg){
		this.status=status.getStatus();
		this.msg=msg;
	}
	
	/**
	 * 只能从工厂中获得实例
	 */
	private AjaxResponse(ResponseStatus status){
		this.status=status.getStatus();
	}
	
	/**
	 * 状态
	 */
	private int status;
	
	/**
	 * 消息
	 */
	private String msg;

	/**
	 * 当前记录数(分页后)
	 */
	private Integer iTotalDisplayRecords;
	
	/**
	 * 总记录数(分页前)
	 */
	private Long iTotalRecords;
	
	/**
	 * 业务数据
	 */
	private List<?> aaData;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(Integer iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public Long getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(Long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public List<?> getAaData() {
		return aaData;
	}

	public void setAaData(List<?> aaData) {
		this.aaData = aaData;
	}
	
}
