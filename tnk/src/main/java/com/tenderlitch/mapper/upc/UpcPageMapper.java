package com.tenderlitch.mapper.upc;

import com.tenderlitch.core.mapper.BaseMapper;
import com.tenderlitch.core.repository.BaseRepository;
import com.tenderlitch.entity.upc.UpcPage;

/**
 * 需要权限控制的页面数据的持久化接口
 * @author tenderliTch
 *
 */
@BaseRepository
public interface UpcPageMapper  extends BaseMapper<UpcPage> {
	/**
	 * 查询时,约定的名称在参数Map中的key值
	 */
	static final String NAME_PARAM_KEY="name";
	/**
	 * 查询时,约定的链接地址在参数Map中的key值
	 */
	static final String URL_PARAM_KEY="url";
	
}
