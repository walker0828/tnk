package com.tenderlitch.service.upc.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tenderlitch.core.service.BaseCRUDService;
import com.tenderlitch.entity.upc.UpcUser;
import com.tenderlitch.mapper.upc.UpcUserMapper;
import com.tenderlitch.service.upc.UpcUserService;
import com.tenderlitch.core.annotation.ServiceLogger;

@Service
@ServiceLogger(serviceModel = "", serviceDesc = "")
public class UpcUserServiceImpl extends BaseCRUDService<UpcUser> implements UpcUserService
{

	@Override
	public boolean isAccountExist(String account) {
		return upcUserMapper.findCountByAccount(account)>0;
	}
	
	@Override
	public UpcUser findUser(String account, String password) {
		Map<String, Object> params=new HashMap<String, Object>(2);
		params.put(UpcUserMapper.ACCOUNT_PARAM_KEY, account);
		params.put(UpcUserMapper.PASSWORD_PARAM_KEY, password);
		return findUniqueByParams(params);
	}

	@Resource
	private UpcUserMapper upcUserMapper;
}
