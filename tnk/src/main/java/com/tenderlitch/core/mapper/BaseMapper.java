package com.tenderlitch.core.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

public interface BaseMapper<T> {
	
	public List<T> find(java.util.Map<String, Object> paramMap);
	  
	public Long insert(T entity);
	  
	public int update(T entity);
	  
	public void delete(T entity);
	  
	public List<T> find(java.util.Map<String, Object> paramMap,RowBounds rowBound);
		  
	/**
	 * 根据主键删除实体对象
	 * @param entity
	 */
	public int deleteByPrimaryKey(Long sid);
	
	/**
	 * 批量删除实体对象，采用Btach批量处理机制
	 * @param collection
	 */
	public void deleteAll(final Collection<T> collection);
	
	/**
	 * 根据主键批量删除实体对象
	 * @param collection
	 */
	public void deleteAllByPrimaryKey(final Collection<Long> sids);
	
	/**
	 * 按照Map参数进行条件删除
	 * @param params
	 */
	public int deleteByParams(Map<String,Object> params);
	
	/**
	 * 批量创建实体对象
	 * @param collection
	 */
	public void insertAll(final Collection<T> collection);
	
	/**
	 * 批量更新实体对象
	 * @param collection
	 */
	public void updateAll(final Collection<T> collection);
	
	/**
	 * 根据主键返回唯一对象
	 * @param sid 序列主键
	 * @return 实体对象
	 */
	public T findByPrimaryKey(Long sid);

	/**
	 * 根据主键数组返回一批对象
	 * @param sids 序列主键数组
	 * @return 实体对象链表
	 */
	public List<T> findByPrimaryKeys(Long[] sids);

	/**
	 * 根据参数返回一个实体对象，一般用于查询具有唯一约束条件的记录
	 * 必须保证传入的参数确实能定位唯一一个对象，否则将iBatis会抛出运行异常
	 * @param params 参数Map对象
	 * @return 实体对象 
	 */
	public T findUniqueByParams(Map<String, Object> params);

	/**
	 * 根据参数返回实体对象集合
	 * @param params 参数Map对象
	 * @return 实体对象链表
	 */
	public List<T> findByParams(Map<String, Object> params);
	
	
	public void insertHistory(Map<String, Object> params);
	
	/**
	 * 与前台数据展示区域绑定的数据汇总接口
	 * @param params
	 * @return
	 */
	public Map<String, Object> getQuerySummary(Map<String, Object> params);
}
