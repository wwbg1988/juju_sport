package com.juju.sport.sync.factory;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import com.juju.sport.common.util.StringUtils;
import com.juju.sport.sync.annotation.TableName;
import com.juju.sport.sync.handler.IDataHandler;

public class DataHandlerFactory {
	
	protected static final Log logger = LogFactory.getLog(DataHandlerFactory.class);
	
	public static Map<String, String> DATA_HANDLER_BEAN_ID_MAP = null;
	
	public static void init() {
		logger.info(" init receive processor ");
		DATA_HANDLER_BEAN_ID_MAP = new HashMap<String, String>();
		
		//WebApplicationContext context = ContextLoaderListener.getCurrentWebApplicationContext();
		ApplicationContext context = ApplicationContextFactory.getApplicationContext();
		
		for(String beanId : context.getBeanDefinitionNames()){
			Object obj = context.getBean(beanId);
			
			if(obj instanceof IDataHandler){
				//IDataHandler processor = (IDataHandler) obj;
				TableName tableName = obj.getClass().getAnnotation(TableName.class);
				if(tableName != null) {
					DATA_HANDLER_BEAN_ID_MAP.put(tableName.value(), beanId);
					logger.info(" table name : " + tableName.value() + " --- beanId : " + beanId);
				}
			}
		}
		logger.info(" init receive processor end ");
		
		
	}
	
	
	/**
	 * 查找发送处理器
	 * @param clientTableName
	 * @return
	 */
	public static IDataHandler findDataProcessor(String clientTableName){
		if(CollectionUtils.isEmpty(DATA_HANDLER_BEAN_ID_MAP)){
			init();
		}
		//WebApplicationContext context = ContextLoaderListener.getCurrentWebApplicationContext();
		ApplicationContext context = ApplicationContextFactory.getApplicationContext();
		String beanId = DATA_HANDLER_BEAN_ID_MAP.get(clientTableName);
		return StringUtils.isEmpty(beanId) ?  null : (IDataHandler)context.getBean(beanId);
	}
}
