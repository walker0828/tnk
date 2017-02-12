package com.tenderlitch.entity.upc;

import com.tenderlitch.core.entity.AbstractEntity;

public class UpcUrl extends AbstractEntity {
	/**
	 * 资源名称
	 */
    private String name;
    /**
     * 资源组主键
     */
    private Integer groupSid;
    /**
     * 资源组名称
     */
    private String groupName;
    /**
     * 资源链接
     */
    private String url;
    /**
     * 资源描述
     */
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGroupSid() {
		return groupSid;
	}

	public void setGroupSid(Integer groupSid) {
		this.groupSid = groupSid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}