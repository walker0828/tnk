package com.tenderlitch.service.upc.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.tenderlitch.core.service.BaseCRUDService;
import com.tenderlitch.entity.upc.UpcRole;
import com.tenderlitch.mapper.upc.UpcRoleMapper;
import com.tenderlitch.service.upc.UpcRoleService;
import com.tenderlitch.service.upc.UpcUserService;

/**
 * 页面权限业务逻辑处理器接口实现
 * @author tenderliTch
 *
 */
@Service
public class UpcRoleServiceImpl extends BaseCRUDService<UpcRole> implements UpcRoleService
{

	/**
	 * 覆盖父类新增方法,增加对角色拥有的页面权限的数据保存逻辑
	 */
	@Override
	public Integer insert(UpcRole upcRole) {
		Integer sid= super.insert(upcRole);
		
		//保存角色拥有的页面数据
		List<Integer> urlSids=upcRole.getUrlSids();
		if(urlSids!=null && urlSids.size()>0){
			insertRoleR2Page(upcRole);
		}
		
		return sid;
	}
	
	/**
	 * 覆盖父类更新方法,增加对角色拥有的页面权限的数据更新逻辑
	 */
	@Override
	//修改角色之后,清空权限数据缓存(权宜做法,本来应该只清空受影响的权限,但是目前没有做到这种逻辑判断)
	@CacheEvict(value="userAvailableUrls",allEntries=true)
	public int update(UpcRole upcRole) {
		int updateRows= super.update(upcRole);
		
		//保存角色拥有的页面数据
		List<Integer> urlSids=upcRole.getUrlSids();
		if(urlSids!=null && urlSids.size()>0){
			updateRoleR2Page(upcRole);
		}
		
		return updateRows;
	}

	/**
	 * 覆盖父类删除方法,增加对角色拥有的页面权限的数据更新逻辑
	 */
	@Override
	//修改角色之后,清空权限数据缓存(权宜做法,本来应该只清空受影响的权限,但是目前没有做到这种逻辑判断)
	@CacheEvict(value="userAvailableUrls",allEntries=true)
	public void delete(UpcRole upcRole) {
		//删除角色拥有的页面数据
		deleteRoleR2Page(upcRole);
		//删除角色和用户的关联信息
		upcUserService.deleteUserR2RoleByRoleSid(upcRole.getSid());
		super.delete(upcRole);
	}

	@Override
	public void insertRoleR2Page(UpcRole upcRole) {
		upcRoleMapper.insertRoleR2Page(upcRole);
	}

	@Override
	//修改角色之后,清空权限数据缓存(权宜做法,本来应该只清空受影响的权限,但是目前没有做到这种逻辑判断)
	@CacheEvict(value="userAvailableUrls",allEntries=true)
	public void updateRoleR2Page(UpcRole upcRole) {
		//先删除所有旧记录
		deleteRoleR2Page(upcRole);
		//再插入所有新纪录
		insertRoleR2Page(upcRole);
	}

	@Override
	public void deleteRoleR2Page(UpcRole upcRole) {
		upcRoleMapper.deleteRoleR2Page(upcRole.getSid());
	}

	@Override
	public List<UpcRole> findRolesForUser() {
		return upcRoleMapper.findAllRolesForUser();
	}

	@Resource
	private UpcRoleMapper upcRoleMapper;
	
	@Resource
	private UpcUserService upcUserService;
}