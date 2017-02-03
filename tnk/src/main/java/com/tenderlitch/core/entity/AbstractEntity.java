package com.tenderlitch.core.entity;

public abstract class AbstractEntity {
	
	/** 主键 */
	protected Integer sid;
	
	/** 乐观锁版本 */
	private Integer version=new Integer(1);
	
	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
