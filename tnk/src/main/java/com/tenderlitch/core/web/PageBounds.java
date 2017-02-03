package com.tenderlitch.core.web;

import org.apache.ibatis.session.RowBounds;

/**
 * 针对MyBatis的分页对象
 * <p>
 * Physical pages： select(new PageBounds(0,10)); Logic paging: select(new RowBounds(0,10));
 * </P>
 * <p>
 * PageBounds.total select count。
 * </p>
 * 
 */
public final class PageBounds extends RowBounds{
	
	public PageBounds(int start, int limit){
		this.limit=limit!=-1?limit:NO_ROW_LIMIT;
		
		//页码数=数据开始的索引/每页条数 的结果的整数部分
		this.offset=start/limit+1;
	}
	
	/**
	 * 要检索的数据条数
	 */
	private int limit;
	
	/**
	 * 要检索的数据的页码
	 */
	private int offset;
	
	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public int getLimit() {
		return limit;
	}
	public static void main(String[] args) {
		System.out.println(Integer.MAX_VALUE);
	}
}
