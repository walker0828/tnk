package com.tenderlitch.mapper.upc;

import com.tenderlitch.core.mapper.BaseMapper;
import com.tenderlitch.core.repository.BaseRepository;
import com.tenderlitch.entity.upc.UpcUser;

/**
 * 系统登陆用户表持久化接口
 * @author tenderliTch
 *
 */
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
	
	/**
	 * 查询account账户的个数(判定是否唯一的标准)
	 * @param account
	 * @return
	 */
	int findCountByAccount(String account);
}
