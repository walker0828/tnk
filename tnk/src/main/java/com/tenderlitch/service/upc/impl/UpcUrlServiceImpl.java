package com.tenderlitch.service.upc.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.tenderlitch.core.service.BaseCRUDService;
import com.tenderlitch.entity.upc.UpcUrl;
import com.tenderlitch.entity.upc.UpcUrlGroup;
import com.tenderlitch.mapper.upc.UpcUrlMapper;
import com.tenderlitch.service.upc.UpcUrlService;

/**
 * 页面权限业务逻辑处理器接口实现
 * @author tenderliTch
 *
 */
@Service
public class UpcUrlServiceImpl extends BaseCRUDService<UpcUrl> implements UpcUrlService
{

	@Override
	public List<UpcUrlGroup> findAllGroup() {
		return upcUrlMapper.findGroup();
	}
	
	@Override
	@Cacheable(value="urlResourceName")
	public String findUrlResourceByUrl(String url) {
		return upcUrlMapper.findUrlResourceByUrl(url);
	}



	@Resource
	private UpcUrlMapper upcUrlMapper;
}