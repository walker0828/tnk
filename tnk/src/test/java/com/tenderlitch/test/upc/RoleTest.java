/**
 * 
 */
package com.tenderlitch.test.upc;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.tenderlitch.service.upc.UpcPageService;

/**
 * 角色业务的单元测试类
 * @author tenderliTch
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/applicationContext.xml", "file:src/main/webapp/WEB-INF/tnk-servlet.xml"})
@Transactional
public class RoleTest {
	
	@Resource
	private UpcPageService upcPageService;
	
	@Test
	public void testpage(){
		System.out.println(upcPageService.findAll());
	}
}
