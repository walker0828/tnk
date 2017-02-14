package com.tenderlitch.service.upc;
import java.util.List;

import com.tenderlitch.core.service.BaseService;
import com.tenderlitch.entity.upc.UpcUrl;
import com.tenderlitch.entity.upc.UpcUrlGroup;

/**
 * 页面权限业务逻辑处理器
 * @author tenderliTch
 *
 */
public interface UpcUrlService extends BaseService<UpcUrl> {

	/**
	 * 查找所有的资源组
	 * @return
	 */
	List<UpcUrlGroup> findAllGroup();

	/**
	 * 查找权限的显示字符串(无权限用户的页面显示用)
	 * @param url
	 * @return
	 */
	String findUrlResourceByUrl(String url);
}

