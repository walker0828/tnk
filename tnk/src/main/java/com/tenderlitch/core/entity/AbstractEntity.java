package com.tenderlitch.core.entity;


import java.util.Date;

public abstract class AbstractEntity {
	
	/** 序列主键 */
	protected Long sid;
	
	/** 乐观锁版本 */
	private Integer version=new Integer(1);
	
	/** 创建者(登录帐号) */
	private String createdBy;

	/** 创建时间 */
	private Date createdDt = new Date();

	/** 最后更新者(登录帐号) */
	private String updatedBy;


	/** 最后更新时间 */
	private Date updatedDt;
	
	/**
	 * 事务处理ID（requestId或者messageId）
	 */
	private String transactionId;
	

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public Date getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
