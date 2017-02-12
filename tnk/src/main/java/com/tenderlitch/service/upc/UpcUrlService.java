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

}

