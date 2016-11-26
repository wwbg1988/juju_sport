package com.juju.sport.logger.service;

import org.springframework.stereotype.Service;

import com.juju.sport.logger.dto.BusinessExceptionDto;
import com.juju.sport.logger.dto.SystemExceptionDto;

@Service
public interface IExceptionLoggerService {

	BusinessExceptionDto popBusinessException();
	
	void pushBusinessException(BusinessExceptionDto ex);
	
	void saveBusinessException(BusinessExceptionDto ex);
	
	SystemExceptionDto popSystemException();
	
	void pushSystemException(SystemExceptionDto ex);
	
	void saveSystemException(SystemExceptionDto ex);
}
