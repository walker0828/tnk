package com.tenderlitch.core.service;

/**
 * 数据服务层接口
 * Created with IntelliJ IDEA.
 * User: Yangcl
 * Date: 13-8-23
 * Time: 下午1:29
 * To change this template use File | Settings | File Templates.
 */

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.tenderlitch.core.web.PageBounds;

public interface BaseService<T>
{
    /**
     * 调阅该实体类的全部数据
     * @param cla 实体类
     * @param <T>  实体对象
     * @return 实体对象集合
     */
    public List<T> find(Map<String, Object> params);

    /**
     * 更新数据集合
     * @param list  实体对象集合
     * @param <T>  实体对象
     */
    public Integer insert(T entity);

    /**
     * 删除数据集合
     * @param list  实体对象集合
     * @param <T>  实体对象
     */
    public void delete(T entity);
    

    public  int update(T entity);

    public Page<T> findByPage(java.util.Map<String, Object> paramMap, PageBounds pageBounds);
    
    /**
	 * 根据主键删除实体对象
	 * @param entity
	 */
	public int deleteByPrimaryKey(Long sid);
	
	/**
	 * 批量删除实体对象
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
	 * 查询全部记录
	 * @return 记录集合
	 */
	public List<T> findAll();
	
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
	 * 必须保证传入的参数确实能定位唯一一个对象，否则将myBatis会抛出运行异常
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
	/**
	 * 与前台数据展示区域绑定的数据汇总接口
	 * @param params
	 * @return
	 */
	public Map<String, Object> getQuerySummary(Map<String, Object> params);
}
