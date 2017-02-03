package com.tenderlitch.core.service;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import com.github.pagehelper.Page;
import com.tenderlitch.core.exception.DaoException;
import com.tenderlitch.core.util.ExceptionUtil;
import com.tenderlitch.core.web.PageBounds;
import com.tenderlitch.core.annotation.MethodLogger;
import com.tenderlitch.core.entity.AbstractEntity;
import com.tenderlitch.core.mapper.BaseMapper;

public class BaseCRUDService<T extends AbstractEntity> implements BaseService<T> {
	
	private final Log logger = LogFactory.getLog(this.getClass());

	private BaseMapper<T> baseMapper;

	protected Class<T> entityClass;

	protected String entityClassName;
	
	protected Object selfProxy() {
		return AppServiceHelper	.findBean(StringUtils.uncapitalize(entityClassName) + "ServiceImpl");
	}
	@MethodLogger(methodDesc="updateSuper()")
	public void updateSuper() {
		logger.debug("updateSuper");
	}
	@Override
	@MethodLogger(methodDesc="查询数据")
	public List<T> find(Map<String, Object> paramMap) {
		try {
			List<T> list = getBaseMapper().find(paramMap);
			return list;
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("DataAccessException", new String[] {paramMap.toString(),"find", ExceptionUtil.getExceptionStackTrace(e, ExceptionUtil.ExceptionLogLength)},e);
		}
	}

	@Override
	@MethodLogger(methodDesc="插入数据")
	public Integer insert(T entity) {
		try {

			getBaseMapper().insert(entity);			
			return entity.getSid();
		} catch (DuplicateKeyException e) {
			logger.error(e);
			throw new DaoException("DuplicateKeyException", new String[] {entity.getSid().toString(),"insert", ExceptionUtil.getExceptionStackTrace(e, ExceptionUtil.ExceptionLogLength)},e);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("InsertDataException", new String[] {entity.getSid().toString(),"insert", ExceptionUtil.getExceptionStackTrace(e, ExceptionUtil.ExceptionLogLength)},e);
		}
	}

	@Override
	@MethodLogger(methodDesc="删除数据")
	public void delete(T entity) {
		try {
			getBaseMapper().delete(entity);
		} catch (DataIntegrityViolationException e) {
			logger.error(e);
			throw new DaoException("DataIntegrityViolationException", new String[] {entity.getSid().toString(), "delete", ExceptionUtil.getExceptionStackTrace(e, ExceptionUtil.ExceptionLogLength)},e);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("DeleteDataException", new String[] {entity.getSid().toString(), "delete", ExceptionUtil.getExceptionStackTrace(e, ExceptionUtil.ExceptionLogLength)},e);
		}
	}

	@Override
	@MethodLogger(methodDesc="更新数据")
	public int update(T entity) {
		int count;
		try {
			count = getBaseMapper().update(entity);
			if (count==0) {
				throw new DaoException("DataNotFoundException", new String[]{entity.getSid().toString(),"update"},null);
			}
			return count;
		} catch (DuplicateKeyException e) {
			logger.error(e);
			throw new DaoException("DuplicateKeyException", new String[] {entity.getSid().toString(),"update", ExceptionUtil.getExceptionStackTrace(e, ExceptionUtil.ExceptionLogLength)},e);
		} catch (DataIntegrityViolationException e) {
			logger.error(e);
			throw new DaoException("DataIntegrityViolationException", new String[] {entity.getSid().toString(),"update", ExceptionUtil.getExceptionStackTrace(e, ExceptionUtil.ExceptionLogLength)},e);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("UpdateDataException", new String[] {entity.getSid().toString(),"update", ExceptionUtil.getExceptionStackTrace(e, ExceptionUtil.ExceptionLogLength)},e);
		}
	}

	@SuppressWarnings("unchecked")
	public BaseCRUDService() {
		try {
			Object genericClz = getClass().getGenericSuperclass();
			if (genericClz instanceof ParameterizedType) {
				entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
						.getActualTypeArguments()[0];
				entityClassName = entityClass.getSimpleName();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	protected BaseMapper<T> getBaseMapper() {
		BaseMapper<T> baseMapper = null;
		if (this.entityClass != null) {
			baseMapper = (BaseMapper<T>) AppServiceHelper
					.findBean(StringUtils.uncapitalize(entityClassName) + "Mapper");
		} else {
			baseMapper = this.baseMapper;
		}
		return baseMapper;
	}
	
	@MethodLogger(methodDesc="分页查询数据")
	public Page<T> findByPage(java.util.Map<String, Object> paramMap, PageBounds pageBounds) {		
		try {
			List<T> list = getBaseMapper().find(paramMap, pageBounds);
			return (Page<T>)list;
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("DataAccessException", new String[] {paramMap.toString(),"findByPage", ExceptionUtil.getExceptionStackTrace(e, ExceptionUtil.ExceptionLogLength)},e);
		}
	}
	
	@MethodLogger(methodDesc="删除数据")
	public int deleteByPrimaryKey(Long sid) {
		try {
			int count = getBaseMapper().deleteByPrimaryKey(sid);
			return count;
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("DeleteDataException", new String[] {sid.toString(), "deleteByPrimaryKey", ExceptionUtil.getExceptionStackTrace(e, ExceptionUtil.ExceptionLogLength)},e);
		}
		
	}
	
	@MethodLogger(methodDesc="批量删除数据")
	public void deleteAll(Collection<T> collection) {
		for(T entity:collection){
			this.delete(entity);			
		}
	}
	
	@MethodLogger(methodDesc="批量删除数据")
	public void deleteAllByPrimaryKey(final Collection<Long> sids) {
		try {
			getBaseMapper().deleteAllByPrimaryKey(sids);
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("DeleteAllException", new String[] {sids.toArray().toString(),"deleteAllByPrimaryKey", ExceptionUtil.getExceptionStackTrace(e, ExceptionUtil.ExceptionLogLength)},e);
		}
	}
	
	@MethodLogger(methodDesc="删除数据")
	public int deleteByParams(Map<String,Object> params) {
		try {
			int count = getBaseMapper().deleteByParams(params);
			return count;
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("DeleteDataException", new String[] {params.toString(),"deleteByParams", ExceptionUtil.getExceptionStackTrace(e, ExceptionUtil.ExceptionLogLength)},e);
		}
	}
	
	@MethodLogger(methodDesc="查询数据")
	public List<T> findAll() {		
		try {
			List<T> list = getBaseMapper().find(null);
			return list;
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("DataAccessException", new String[] {"", "findAll", ExceptionUtil.getExceptionStackTrace(e, ExceptionUtil.ExceptionLogLength)},e);
		}
	}
	
	@MethodLogger(methodDesc="批量插入数据")
	public void insertAll(Collection<T> collection) {
		for(T entity:collection){
			this.insert(entity);			
		}
	}
	
	@MethodLogger(methodDesc="批量更新数据")
	public void updateAll(Collection<T> collection) {
		for(T entity:collection){
			this.update(entity);			
		}
	}
	
	@MethodLogger(methodDesc="查询数据")
	public T findByPrimaryKey(Long sid) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("sid", sid);
			List<T> list =getBaseMapper().find(params);
			if (list!=null && list.size()>0) {
				if (list.size()==1) {
					return list.get(0);
				} else {
					throw new DaoException("MultiDataFoundException", new String[]{sid.toString(),"findByPrimaryKey"},null);
				}
			} else {
				throw new DaoException("DataNotFoundException", new String[]{sid.toString(),"findByPrimaryKey"},null);
			}
		} catch (DaoException e) {
			logger.error(e);
			throw e;
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("DataAccessException", new String[] {sid.toString(),"findByPrimaryKey", ExceptionUtil.getExceptionStackTrace(e, ExceptionUtil.ExceptionLogLength)},e);
		}
	}

	@MethodLogger(methodDesc="查询数据")
	public List<T> findByPrimaryKeys(Long[] sids) {
		try {
			List<T> list = getBaseMapper().findByPrimaryKeys(sids);
			return list;
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("DataAccessException", new String[] {sids.toString(),"findByPrimaryKeys", ExceptionUtil.getExceptionStackTrace(e, ExceptionUtil.ExceptionLogLength)},e);
		}
	}

	@MethodLogger(methodDesc="查询数据")
	public T findUniqueByParams(Map<String, Object> params){
		try {
		    List<T> list= getBaseMapper().find(params);
		    if(list==null||list.size()==0){
		        return null;
		    } else if(list.size()==1){
		        return list.get(0);
		    }else{
		        throw new DaoException("MultiDataFoundException", new String[] {params.toString(),"findUniqueByParams"},null);
		    }
		} catch (DaoException e) {
			logger.error(e);
			throw e;
		}catch (Exception e) {
			logger.error(e);
			throw new DaoException("DataAccessException", new String[] {params.toString(), "findUniqueByParams", ExceptionUtil.getExceptionStackTrace(e, ExceptionUtil.ExceptionLogLength)},e);
		}
	}

	@MethodLogger(methodDesc="查询数据")
	public List<T> findByParams(Map<String, Object> params) {
		try {
			List<T>  list = getBaseMapper().find(params);
			return list;
		} catch (Exception e) {
			logger.error(e);
			throw new DaoException("DataAccessException", new String[] {params.toString(), "findByParams", ExceptionUtil.getExceptionStackTrace(e, ExceptionUtil.ExceptionLogLength)},e);
		}
	}
	
	@MethodLogger(methodDesc="插入历史数据")
	public void insertHistory(Map<String, Object> params) {
		getBaseMapper().insertHistory(params);
	}
	
	/**
	 * 与前台数据展示区域绑定的数据汇总接口
	 * @param params
	 * @return
	 */
	public Map<String, Object> getQuerySummary(Map<String, Object> params) {
		Map<String, Object> summaryResult = new HashMap<String, Object>();
		Map<String, Object> summaryMap = getBaseMapper().getQuerySummary(params);
		Iterator<Entry<String,Object>> it = summaryMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String, Object> entry =  (Entry<String, Object>) it.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            summaryResult.put(key, value);
        }
		return summaryResult;
	}
}
