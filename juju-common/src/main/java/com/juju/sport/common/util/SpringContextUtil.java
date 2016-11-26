package com.juju.sport.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	  public void setApplicationContext(ApplicationContext context)
	  {
	    applicationContext = context;
	  }

	  public static ApplicationContext getApplicationContext()
	  {
	    if (applicationContext == null)
	      throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil");
	    return applicationContext;
	  }

	  public static <T> T getBean(String name)
	  {
	    return (T) applicationContext.getBean(name);
	  }

}
