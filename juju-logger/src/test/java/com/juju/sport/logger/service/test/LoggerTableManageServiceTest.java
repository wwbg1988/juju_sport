package com.juju.sport.logger.service.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.juju.sport.logger.service.ILoggerTableManageService;
import com.juju.sport.logger.test.BaseTestCase;

public class LoggerTableManageServiceTest extends BaseTestCase{

	@Autowired
	private ILoggerTableManageService service;
	
	@Test
	public void testCreateTable(){
		service.createTodayBussinessExceptionLoggerTableIfNotExist();
		//service.createTodaySystemExceptionLoggerTableIfNotExist();
	}
}
