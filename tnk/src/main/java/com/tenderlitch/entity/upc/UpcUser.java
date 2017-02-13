package com.tenderlitch.entity.upc;

import java.util.ArrayList;
import java.util.List;

import com.tenderlitch.core.entity.AbstractEntity;

public class UpcUser extends AbstractEntity {
    /**
     * 登录账号
     */
    private String account;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 用户邮箱,用于找回密码和其他未预见的联系只用
     */
    private String email;
    /**
     * 用户描述,由系统管理员或者本人添加
     */
    private String description;
    /**
     * 用户的角色sid,用来接收页面参数
     */
    private List<Integer> roleSids;
    /**
     * 用户的角色,用于页面显示
     */
    private List<UpcRole> roles;
    /**
     * 用户的角色名称的拼接字符串,用于页面显示
     */
    private String rolesString;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Integer> getRoleSids() {
		return roleSids;
	}

	public void setRoleSids(List<Integer> roleSids) {
		this.roleSids = roleSids;
	}

	public List<UpcRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UpcRole> roles) {
		this.roles = roles;
		if(roles!=null){
			if(roleSids==null){
				roleSids=new ArrayList<Integer>(roles.size());
			}
			StringBuilder sb=new StringBuilder();
			for(UpcRole role : roles){
				roleSids.add(role.getSid());
				sb.append("[").append(role.getName()).append("] ");
			}
			rolesString=sb.toString();
		}else{
			roleSids=null;
			rolesString=null;
		}
	}

	public String getRolesString() {
		return rolesString;
	}

	public void setRolesString(String rolesString) {
		this.rolesString = rolesString;
	}
	
}