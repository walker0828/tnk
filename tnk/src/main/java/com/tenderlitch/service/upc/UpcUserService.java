package com.tenderlitch.service.upc;
import com.tenderlitch.core.service.BaseService;
import com.tenderlitch.entity.upc.UpcUser;

/**
 * 系统登录用户业务逻辑处理器
 * @author tenderliTch
 *
 */
public interface UpcUserService extends BaseService<UpcUser> {

	/**
	 * 判断用户是否已经存在
	 * @param account 登陆账户
	 * @return
	 */
	boolean isAccountExist(String account);

	/**
	 * 通过账户和密码查找用户
	 * @param account 账号
	 * @param password 密码
	 * @return
	 */
	UpcUser findUser(String account, String password);
	
	/**
	 * 通过角色Sid删除用户的角色信息(供删除角色时使用)
	 * @param roleSid
	 */
	void deleteUserR2RoleByRoleSid(Integer roleSid);
}

