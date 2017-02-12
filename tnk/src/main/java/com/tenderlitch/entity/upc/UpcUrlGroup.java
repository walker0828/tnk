/**
 * 
 */
package com.tenderlitch.entity.upc;

import java.util.List;

import com.tenderlitch.core.entity.AbstractEntity;

/**
 * @author tenderliTch
 * 资源链接组
 */
public class UpcUrlGroup extends AbstractEntity {
	private String name;
	
	private List<UpcUrl> urls;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UpcUrl> getUrls() {
		return urls;
	}

	public void setUrls(List<UpcUrl> urls) {
		this.urls = urls;
	}
	
}
