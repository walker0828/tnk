package com.tenderlitch.entity.upc;

import java.util.ArrayList;
import java.util.List;

import com.tenderlitch.core.entity.AbstractEntity;

public class UpcRole extends AbstractEntity {
	/**
	 * 角色名称(用于用户辨识)
	 */
    private String name;
    /**
     * 角色描述(备注)
     */
    private String description;
    /**
     * 角色拥有权限的资源(用于接收页面参数)
     */
    private List<Integer> urlSids;
    /**
     * 角色拥有权限的资源(用于向页面传递数据)
     */
    private List<UpcUrl> urls;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public List<Integer> getUrlSids() {
		return urlSids;
	}

	public void setUrlSids(List<Integer> urlSids) {
		this.urlSids = urlSids;
	}

	public List<UpcUrl> getUrls() {
		return urls;
	}

	public void setUrls(List<UpcUrl> urls) {
		this.urls = urls;
		//同步pageSids
		if(urls!=null && urls.size()>0){
			if(urlSids==null){
				urlSids=new ArrayList<Integer>(urls.size());
			}
			for(UpcUrl url : urls){
				urlSids.add(url.getSid());
			}
		}else{
			urlSids=null;
		}
	}
}