package com.juju.sport.logger.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.logger.dto.SystemExceptionDto;
import com.juju.sport.logger.service.IExceptionLoggerService;

@Service
public class SystemExceptionHandler implements Runnable {

	protected static final Log logger = LogFactory.getLog(SystemExceptionHandler.class);

	@Autowired
	private IExceptionLoggerService exceptionLoggerService;
	
	@Override
	public void run() {
		while(true){
			try { 
				//logger.debug("system exception handler");
				handleException();				
			} catch (Throwable e){
				continue;
			}
			
		}	
	}

	private void handleException() {
		SystemExceptionDto systemException = exceptionLoggerService.popSystemException();
		if(systemException != null){
			exceptionLoggerService.saveSystemException(systemException);
			logger.debug("save exception : " + systemException);
		}else{
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
