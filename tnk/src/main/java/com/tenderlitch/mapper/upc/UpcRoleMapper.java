package com.tenderlitch.mapper.upc;

import com.tenderlitch.core.mapper.BaseMapper;
import com.tenderlitch.core.repository.BaseRepository;
import com.tenderlitch.entity.upc.UpcRole;

/**
 * 角色权限数据的持久化接口
 * @author tenderliTch
 *
 */
@BaseRepository
public interface UpcRoleMapper  extends BaseMapper<UpcRole> {
	/**
	 * 新增角色拥有的页面权限数据
	 * @param upcRole
	 */
	void insertRoleR2Page(UpcRole upcRole);

	/**
	 * 删除主键值为sid的角色对应的所有页面权限(供更新页面权限时使用)
	 * @param sid
	 */
	void deleteRoleR2Page(Integer sid);
}
