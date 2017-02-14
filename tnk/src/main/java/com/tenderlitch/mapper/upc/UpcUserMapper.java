package com.tenderlitch.mapper.upc;

import java.util.Set;

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
	
	/**
	 * 插入用户的角色信息
	 * @param user
	 */
	void insertUserR2Role(UpcUser user);
	
	/**
	 * 根据用户Sid删除用户的角色信息
	 * @param userSid
	 */
	void deleteUserR2RoleByUserSid(Integer userSid);
	
	/**
	 * 根据角色Sid删除用户的角色信息
	 * @param userSid
	 */
	void deleteUserR2RoleByRoleSid(Integer roleSid);
	
	/**
	 * 根据用户的Sid查找有权限的Urls
	 * @param userSid
	 * @return
	 */
	Set<String> findUserAvailableUrls(Integer userSid);
	
}
