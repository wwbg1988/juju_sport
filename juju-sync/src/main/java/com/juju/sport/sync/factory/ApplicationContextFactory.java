package com.juju.sport.sync.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextFactory {
	
	public  ApplicationContextFactory(){
	}

	private static ApplicationContext context = new ClassPathXmlApplicationContext("spring-config/applicationContext.xml");
	
	public static ApplicationContext getApplicationContext(){
		return context;
	}	
	
}
