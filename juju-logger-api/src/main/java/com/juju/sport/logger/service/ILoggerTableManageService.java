package com.juju.sport.logger.service;

import org.springframework.stereotype.Service;

@Service
public interface ILoggerTableManageService {

	void createTodayBussinessExceptionLoggerTableIfNotExist();
	
	void createTodaySystemExceptionLoggerTableIfNotExist();
}
