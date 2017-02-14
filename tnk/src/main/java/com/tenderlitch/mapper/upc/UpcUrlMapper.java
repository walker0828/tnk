package com.tenderlitch.mapper.upc;

import java.util.List;

import com.tenderlitch.core.mapper.BaseMapper;
import com.tenderlitch.core.repository.BaseRepository;
import com.tenderlitch.entity.upc.UpcUrl;
import com.tenderlitch.entity.upc.UpcUrlGroup;

/**
 * 需要权限控制的页面数据的持久化接口
 * @author tenderliTch
 *
 */
@BaseRepository
public interface UpcUrlMapper  extends BaseMapper<UpcUrl> {
	/**
	 * 查询时,约定的名称在参数Map中的key值
	 */
	static final String NAME_PARAM_KEY="name";
	/**
	 * 查询时,约定的链接地址在参数Map中的key值
	 */
	static final String URL_PARAM_KEY="url";
	
	/**
	 * 获取所有资源链接组
	 * @return
	 */
	List<UpcUrlGroup> findGroup();
	
	/**
	 * 通过链接地址获得资源的显示字符串
	 * @param url
	 * @return
	 */
	String findUrlResourceByUrl(String url);
	
}
