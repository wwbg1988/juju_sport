package com.juju.sport.logger.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.logger.dao.LoggerTableManageDao;
import com.juju.sport.logger.service.ILoggerTableManageService;
import com.juju.sport.logger.util.SysLoggerUtil;

@Service
public class LoggerTableManageServiceImpl implements ILoggerTableManageService {

	@Autowired
	private LoggerTableManageDao tableManageDao;
	
	@Override
	public void createTodayBussinessExceptionLoggerTableIfNotExist() {
		if(existTodayBusinessExceptionLoggerTable()){
			return;
		}
		tableManageDao.createBussinessExceptionTable(SysLoggerUtil.getTodayBusinessExceptionLoggerTableName());
	}
	
	@Override
	public void createTodaySystemExceptionLoggerTableIfNotExist() {
		if(existTodaySystemExceptionLoggerTable()){
			return;
		}
		tableManageDao.createSystemExceptionTable(SysLoggerUtil.getTodaySystemExceptionLoggerTableName());
	}

	private boolean existTodaySystemExceptionLoggerTable() {
		return tableManageDao.count(SysLoggerUtil.getTodaySystemExceptionLoggerTableName()) > 0;
	}

	private boolean existTodayBusinessExceptionLoggerTable() {
		return tableManageDao.count(SysLoggerUtil.getTodayBusinessExceptionLoggerTableName()) > 0;
	}

}
