/**
 * 
 */
package com.tenderlitch.test.upc;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.tenderlitch.entity.upc.UpcUrlGroup;
import com.tenderlitch.service.upc.UpcUrlService;
import com.tenderlitch.test.core.BaseTest;

/**
 * 角色业务的单元测试类
 * @author tenderliTch
 *
 */
public class UrlTest extends BaseTest{
	
	@Resource
	private UpcUrlService upcUrlService;
	
	@Test
	public void testFindGroup(){
		List<UpcUrlGroup> groups=upcUrlService.findAllGroup();
		System.out.println(groups.size());
	}
	
}
