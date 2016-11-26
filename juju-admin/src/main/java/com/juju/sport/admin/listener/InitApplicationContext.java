package com.juju.sport.admin.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.juju.sport.admin.init.InitAppDataHandler;
import com.juju.sport.common.util.PropertiesUtils;
import com.juju.sport.common.util.SpringContextUtil;


public class InitApplicationContext implements ServletContextListener {

	protected static final Log logger = LogFactory.getLog(InitApplicationContext.class);
	
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("###### juju-admin application start ######");
		org.apache.ibatis.logging.LogFactory.useLog4JLogging(); 
		PropertiesUtils.printPropertyInfo();
		InitAppDataHandler handler = SpringContextUtil.getBean("initAppDataHandler");
		handler.prepareDate();
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
