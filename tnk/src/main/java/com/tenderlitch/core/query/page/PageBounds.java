package com.tenderlitch.core.query.page;

import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Physical pages, RowBounds Logic paging.
 * <p>
 * Physical pages： select(new PageBounds(0,10)); Logic paging: select(new RowBounds(0,10));
 * </P>
 * <p>
 * PageBounds.total select count。
 * </p>
 * 
 * @author jiwei.liu
 * @since 1.0.0
 */
public final class PageBounds extends RowBounds implements Pageable{

	private int total = 0;
	
	private boolean isTotal = true;

	private int page;
	
	private PageRequest pageRequest;

	//private Collection<T> resultList;

	public PageBounds(int page, int limit) {
		super(page, limit);
		this.page = page;
		this.pageRequest = new PageRequest(page-1, limit);
	}
	
	public PageBounds(int page, int limit,Sort sort) {
		super(page, limit);
		this.page = page;
		this.pageRequest = new PageRequest(page-1, limit,sort);
	}

	public PageBounds(int page, int limit, boolean isTotal) {
		this(page, limit);
		this.isTotal = isTotal;
	}

	public PageRequest getPageRequest() {
		return pageRequest;
	}

	/**
	 * Calculate the total number of pages ，when isTotal = true.
	 * 
	 * @return
	 */
	public int getTotalPages() {
		int totalPages = 0;

		if (this.total % this.getLimit() == 0) {
			totalPages = this.total / this.getLimit();
		} else {
			totalPages = (this.total / this.getLimit()) + 1;
		}

		return totalPages;
	}

	public int getPage() {
		return page;
	}


	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}


	public boolean isTotal() {
		return isTotal;
	}


	public int offset() {
		if (getOffset() + this.getLimit() > total) {
			return total;
		} else {
			return getOffset() + this.getLimit();
		}
	}

	@Override
	public Pageable first() {
		return pageRequest.first();
	}

	@Override
	public int getPageNumber() {		
		return pageRequest.getPageNumber();
	}

	@Override
	public int getPageSize() {		
		return pageRequest.getPageSize();
	}

	@Override
	public Sort getSort() {		
		return pageRequest.getSort();
	}

	@Override
	public boolean hasPrevious() {		
		return pageRequest.hasPrevious();
	}

	@Override
	public Pageable next() {		
		return pageRequest.next();
	}

	@Override
	public Pageable previousOrFirst() {		
		return pageRequest.previousOrFirst();
	}

	
}
