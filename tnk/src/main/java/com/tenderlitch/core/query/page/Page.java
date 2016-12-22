package com.tenderlitch.core.query.page;

import java.util.List;

/**
 * 分页对象
 * @author tenderliTch
 *
 * @param <T>
 */
public class Page<T> {
	private Integer results;
	private Long totalProperty;
	private List<T> items;
	
	public Page(com.github.pagehelper.Page<T> page){
		this.items=page.getResult();
		this.totalProperty=page.getTotal();
		this.results=items.size();
	}

	public Integer getResults() {
		return results;
	}

	public void setResults(Integer results) {
		this.results = results;
	}

	public Long getTotalProperty() {
		return totalProperty;
	}

	public void setTotalProperty(Long totalProperty) {
		this.totalProperty = totalProperty;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
	
	
}
