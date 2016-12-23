package com.tenderlitch.core.service;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

@Component
public class AppServiceHelper implements ApplicationContextAware {

	private static final Log logger = LogFactory.getLog(AppServiceHelper.class);

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		AppServiceHelper.applicationContext = applicationContext;
	}

	public static Object findBean(String beanId) throws NoSuchBeanDefinitionException {
		Object service = applicationContext.getBean(beanId);
		return service;
	}

	public static String getMessage(String key, Object[] params, Locale locale) {
		
		if (locale==null) locale = new java.util.Locale("zh","CN");
		//从后台代码获取国际化信息
		String i18n = "";
		try {
			i18n = applicationContext.getMessage(key, params, locale);
		} catch (NoSuchMessageException e) {
			logger.warn("i18n definition for [" + key + "] not found in properties file.");
		}
		return i18n;
	}
	
}
