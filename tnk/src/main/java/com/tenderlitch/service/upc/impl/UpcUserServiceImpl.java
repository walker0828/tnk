package com.tenderlitch.service.upc.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.tenderlitch.core.service.BaseCRUDService;
import com.tenderlitch.entity.upc.UpcUser;
import com.tenderlitch.mapper.upc.UpcUserMapper;
import com.tenderlitch.service.upc.UpcUserService;

/**
 * 系统登录用户业务逻辑处理器接口实现
 * @author tenderliTch
 *
 */
@Service
public class UpcUserServiceImpl extends BaseCRUDService<UpcUser> implements UpcUserService
{

	/**
	 * 判断账号是否存在
	 */
	@Override
	public boolean isAccountExist(String account) {
		return upcUserMapper.findCountByAccount(account)>0;
	}
	
	/**
	 * 根据账号和密码查找用户
	 */
	@Override
	public UpcUser findUser(String account, String password) {
		Map<String, Object> params=new HashMap<String, Object>(2);
		params.put(UpcUserMapper.ACCOUNT_PARAM_KEY, account);
		params.put(UpcUserMapper.PASSWORD_PARAM_KEY, password);
		return findUniqueByParams(params);
	}

	/**
	 * 插入新用户数据,并保存用户的角色信息
	 */
	@Override
	public Integer insert(UpcUser user) {
		Integer userSid= super.insert(user);
		insertUserR2Role(user);
		return userSid;
	}

	/**
	 * 更新用户数据,并更新用户的角色信息
	 */
	@Override
	//更新用户之后,将原来的权限缓存清除,直到下次请求权限数据时再次加载
	@CacheEvict(value="userAvailableUrls",key="#user.getSid()")
	public int update(UpcUser user) {
		deleteUserR2RoleByUser(user.getSid());
		insertUserR2Role(user);
		return super.update(user);
	}
	
	/**
	 * 删除数据,并删除用户的角色信息
	 */
	@Override
	//删除用户之后,将原来的权限缓存清除
	@CacheEvict(value="userAvailableUrls",key="#user.getSid()")
	public void delete(UpcUser user) {
		deleteUserR2RoleByUser(user.getSid());
		super.delete(user);
	}

	@Override
	public void deleteUserR2RoleByRoleSid(Integer roleSid) {
		upcUserMapper.deleteUserR2RoleByRoleSid(roleSid);
	}

	@Override
	@Cacheable(value="userAvailableUrls")//缓存用户具有权限的链接
	public Set<String> getUserAvailableUrls(Integer userSid) {
		return upcUserMapper.findUserAvailableUrls(userSid);
	}

	private void deleteUserR2RoleByUser(Integer userSid){
		upcUserMapper.deleteUserR2RoleByUserSid(userSid);
	}

	private void insertUserR2Role(UpcUser user){
		upcUserMapper.insertUserR2Role(user);
	}

	@Resource
	private UpcUserMapper upcUserMapper;
}
