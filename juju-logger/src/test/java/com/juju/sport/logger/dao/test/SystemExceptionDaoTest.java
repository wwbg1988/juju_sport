package com.juju.sport.logger.dao.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.juju.sport.common.util.UUIDGenerator;
import com.juju.sport.logger.dao.SystemExceptionDao;
import com.juju.sport.logger.dto.SystemExceptionDto;
import com.juju.sport.logger.test.BaseTestCase;

public class SystemExceptionDaoTest extends BaseTestCase {

	@Autowired
	private SystemExceptionDao systemDao;
	
	@Test
	public void insertTest(){
		SystemExceptionDto systemDto = new SystemExceptionDto();
		systemDto.setCreateTime(new Date());
		systemDto.setErrorMessage("test message");
		systemDto.setErrorStackTrace("errorStackTrace");
		systemDto.setId(UUIDGenerator.getUUID());
		
		systemDao.saveException(systemDto);
	}
}
