package com.juju.sport.logger.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.logger.mapper.LoggerTableManageMapper;

@Repository
public class LoggerTableManageDao {

	@Autowired
	private LoggerTableManageMapper mapper;
	
	public long count(String tableName){
		return mapper.count(tableName);
	}
	
	public int createBussinessExceptionTable(String tableName){
		return mapper.createBussinessExceptionTable(tableName);
	}
	
	public int createSystemExceptionTable(String tableName){
		return mapper.createSystemExceptionTable(tableName);
	}
}
