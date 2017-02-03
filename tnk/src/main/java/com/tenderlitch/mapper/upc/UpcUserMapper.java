package com.tenderlitch.mapper.upc;

import com.tenderlitch.core.mapper.BaseMapper;
import com.tenderlitch.core.repository.BaseRepository;
import com.tenderlitch.entity.upc.UpcUser;

@BaseRepository
public interface UpcUserMapper  extends BaseMapper<UpcUser> {
	/**
	 * 查询时,约定的账户在参数Map中的key值
	 */
	static final String ACCOUNT_PARAM_KEY="account";
	/**
	 * 查询时,约定的账户密码在参数Map中的key值
	 */
	static final String PASSWORD_PARAM_KEY="password";
	
	int findCountByAccount(String account);
}
