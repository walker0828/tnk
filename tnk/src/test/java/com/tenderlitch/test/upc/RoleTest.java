/**
 * 
 */
package com.tenderlitch.test.upc;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.tenderlitch.entity.upc.UpcRole;
import com.tenderlitch.service.upc.UpcRoleService;
import com.tenderlitch.test.core.BaseTest;

/**
 * 角色业务的单元测试类
 * @author tenderliTch
 *
 */
public class RoleTest extends BaseTest{
	
	@Resource
	private UpcRoleService upcRoleService;
	
	@Test
	//@Rollback(false)
	public void testInsert(){
		UpcRole role=new UpcRole();
		role.setDescription("测试");
		role.setName("测试角色");
		List<Integer> pageSids=new ArrayList<Integer>();
		pageSids.add(1);
		pageSids.add(2);
		role.setUrlSids(pageSids);
		upcRoleService.insert(role);
	}
	
	@Test
	public void testFind(){
		List<UpcRole> list=upcRoleService.findAll();
		Assert.assertNotNull(list);
		for(UpcRole role : list){
			System.out.println(role.getUrls());
		}
	}
	
	@Test
	//@Rollback(false)
	public void testUpdate(){
		UpcRole role=new UpcRole();
		role.setSid(3);
		role.setVersion(1);
		role.setDescription("操作人员");
		role.setName("日常工作人员");
		List<Integer> pageSids=new ArrayList<Integer>();
		pageSids.add(2);
		role.setUrlSids(pageSids);
		upcRoleService.update(role);
	}
	
	@Test
	@Rollback(false)
	public void testDelete(){
		UpcRole role=new UpcRole();
		role.setSid(3);
		role.setVersion(1);
		upcRoleService.delete(role);
	}
}
