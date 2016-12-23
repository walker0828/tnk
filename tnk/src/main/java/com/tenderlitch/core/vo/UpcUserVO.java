package com.tenderlitch.core.vo;
import java.io.Serializable;

public class UpcUserVO   implements Serializable 
{
	private static final long serialVersionUID = 161587417963558999L;
	
	private Long sid;
	
	private String upcUserId;
	
	public String getUpcUserId() {
		return upcUserId;
	}

	public void setUpcUserId(String upcUserId) {
		this.upcUserId = upcUserId;
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

}

