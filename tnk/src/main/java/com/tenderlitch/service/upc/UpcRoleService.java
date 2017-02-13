package com.tenderlitch.service.upc;
import java.util.List;

import com.tenderlitch.core.service.BaseService;
import com.tenderlitch.entity.upc.UpcRole;

/**
 * 角色权限业务逻辑处理器
 * @author tenderliTch
 *
 */
public interface UpcRoleService extends BaseService<UpcRole> {
	/**
	 * 新增角色拥有的页面权限数据
	 * @param upcRole 角色
	 */
	void insertRoleR2Page(UpcRole upcRole);
	
	/**
	 * 更新角色拥有的页面权限数据
	 * @param upcRole 角色
	 */
	void updateRoleR2Page(UpcRole upcRole);
	
	/**
	 * 删除角色拥有的页面权限数据
	 * @param upcRole
	 */
	void deleteRoleR2Page(UpcRole upcRole);

	/**
	 * 为用户界面编辑角色时查询所有角色信息,只包含roleName和sid
	 * @return
	 */
	List<UpcRole> findRolesForUser();
}

