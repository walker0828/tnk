package com.tenderlitch.entity.su;

import com.tenderlitch.core.entity.AbstractEntity;
import java.util.Date;

public class User extends AbstractEntity {
    private String pin;

    private String userNameCn;

    private String userNameEn;

    private String userNameVn;

    private Short userState;

    private Short country;

    private Short sex;

    private String password;

    private String email;

    private String extTel;

    private String phone1;

    private String phone2;

    private Short groupId;

    private Short shiftId;

    private Date pwdModifyDate;

    private Date createdTimestamp;

    private Date updatedTimestamp;

    private String shortName;

    private String branchCompanyId;

    private String workshopId;

    private String deptCode;

    private Long staffSid;

    private Long orgSid;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getUserNameCn() {
        return userNameCn;
    }

    public void setUserNameCn(String userNameCn) {
        this.userNameCn = userNameCn;
    }

    public String getUserNameEn() {
        return userNameEn;
    }

    public void setUserNameEn(String userNameEn) {
        this.userNameEn = userNameEn;
    }

    public String getUserNameVn() {
        return userNameVn;
    }

    public void setUserNameVn(String userNameVn) {
        this.userNameVn = userNameVn;
    }

    public Short getUserState() {
        return userState;
    }

    public void setUserState(Short userState) {
        this.userState = userState;
    }

    public Short getCountry() {
        return country;
    }

    public void setCountry(Short country) {
        this.country = country;
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExtTel() {
        return extTel;
    }

    public void setExtTel(String extTel) {
        this.extTel = extTel;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public Short getGroupId() {
        return groupId;
    }

    public void setGroupId(Short groupId) {
        this.groupId = groupId;
    }

    public Short getShiftId() {
        return shiftId;
    }

    public void setShiftId(Short shiftId) {
        this.shiftId = shiftId;
    }

    public Date getPwdModifyDate() {
        return pwdModifyDate;
    }

    public void setPwdModifyDate(Date pwdModifyDate) {
        this.pwdModifyDate = pwdModifyDate;
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Date getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Date updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(String workshopId) {
        this.workshopId = workshopId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Long getStaffSid() {
        return staffSid;
    }

    public void setStaffSid(Long staffSid) {
        this.staffSid = staffSid;
    }

    public Long getOrgSid() {
        return orgSid;
    }

    public void setOrgSid(Long orgSid) {
        this.orgSid = orgSid;
    }
}