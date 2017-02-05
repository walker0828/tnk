/**
 * 
 */
package com.tenderlitch.test.core;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 单元测试类的父类,持有一些junit+spring单元测试的配置信息
 * @author tenderliTch
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/applicationContext.xml"})
@Transactional
public class BaseTest {}
