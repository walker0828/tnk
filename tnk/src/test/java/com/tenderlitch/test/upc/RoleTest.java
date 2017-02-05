/**
 * 
 */
package com.tenderlitch.test.upc;

import javax.annotation.Resource;

import org.junit.Test;

import com.tenderlitch.service.upc.UpcPageService;
import com.tenderlitch.test.core.BaseTest;

/**
 * 角色业务的单元测试类
 * @author tenderliTch
 *
 */
public class RoleTest extends BaseTest{
	
	@Resource
	private UpcPageService upcPageService;
	
	@Test
	public void testpage(){
		System.out.println(upcPageService.findAll());
	}
}
