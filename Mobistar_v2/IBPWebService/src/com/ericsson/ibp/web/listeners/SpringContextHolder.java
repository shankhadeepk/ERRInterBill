package com.ericsson.ibp.web.listeners;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author ejyoban
 * This bean is used to hold the spring application context
 */

public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}


