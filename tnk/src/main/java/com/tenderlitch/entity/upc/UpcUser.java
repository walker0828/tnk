package com.tenderlitch.entity.upc;

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
}