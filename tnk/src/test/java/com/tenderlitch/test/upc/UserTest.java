/**
 * 
 */
package com.tenderlitch.test.upc;


import javax.annotation.Resource;

import org.junit.Test;

import com.tenderlitch.service.upc.UpcUserService;
import com.tenderlitch.test.core.BaseTest;

/**
 * 角色业务的单元测试类
 * @author tenderliTch
 *
 */
public class UserTest extends BaseTest{
	
	@Resource
	private UpcUserService upcUserService;
	
	@Test
	//@Rollback(false)
	public void testGetUserAvailableUrls(){
		System.out.println(upcUserService.getUserAvailableUrls(9));
		System.out.println(upcUserService.getUserAvailableUrls(9));
	}
}
