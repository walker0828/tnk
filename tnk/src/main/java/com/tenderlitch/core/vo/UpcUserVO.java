package com.tenderlitch.core.vo;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class UpcUserVO   implements Serializable 
{
	private static final long serialVersionUID = 161587417963558999L;
	
	private Long sid;
	
	private String upcUserId;
	
	private String upcUserGroup;
	
	private String upcUserSeq;
	
	private String facilityIds;
	
	private String l4FacilityIds;
	
	private String upcUserPost;
	
	private String transactionId;
	
	private String upcUserDeptId;
	
	private String password;
	
	//用户所有数据权限,键值对,键为sgai_su_sec_role_r2_business的OBJECT,值为业务表sid
	private Map<String,List<String>> businessIDs;

	public String getUpcUserDeptId() {
		return upcUserDeptId;
	}

	public void setUpcUserDeptId(String upcUserDeptId) {
		this.upcUserDeptId = upcUserDeptId;
	}

	public String getUpcUserPost() {
		return upcUserPost;
	}

	public void setUpcUserPost(String upcUserPost) {
		this.upcUserPost = upcUserPost;
	}

	public String getUpcUserSeq() {
		return upcUserSeq;
	}

	public void setUpcUserSeq(String upcUserSeq) {
		this.upcUserSeq = upcUserSeq;
	}

	public String getUpcUserId() {
		return upcUserId;
	}

	public void setUpcUserId(String upcUserId) {
		this.upcUserId = upcUserId;
	}

	public String getUpcUserGroup() {
		return upcUserGroup;
	}

	public void setUpcUserGroup(String upcUserGroup) {
		this.upcUserGroup = upcUserGroup;
	}

	/**
	 * @return the facilityIds
	 */
	public String getFacilityIds() {
		return facilityIds;
	}

	/**
	 * @param facilityIds the facilityIds to set
	 */
	public void setFacilityIds(String facilityIds) {
		this.facilityIds = facilityIds;
	}

	/**
	 * @return the l4FacilityIds
	 */
	public String getL4FacilityIds() {
		return l4FacilityIds;
	}

	/**
	 * @param facilityIds the l4FacilityIds to set
	 */
	public void setL4FacilityIds(String facilityIds) {
		l4FacilityIds = facilityIds;
	}

	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Map<String, List<String>> getBusinessIDs() {
		return businessIDs;
	}

	public void setBusinessIDs(Map<String, List<String>> businessIDs) {
		this.businessIDs = businessIDs;
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}

