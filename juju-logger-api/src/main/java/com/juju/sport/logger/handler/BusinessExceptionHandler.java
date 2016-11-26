package com.juju.sport.logger.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.logger.dto.BusinessExceptionDto;
import com.juju.sport.logger.service.IExceptionLoggerService;

@Service
public class BusinessExceptionHandler implements Runnable {
	
	protected static final Log logger = LogFactory.getLog(BusinessExceptionHandler.class);

	@Autowired
	private IExceptionLoggerService exceptionLoggerService;
	
	@Override
	public void run() {
		while(true){
			try { 
				//logger.debug("business exception handler");
				handleException();				
			} catch (Throwable e){
				continue;
			}
			
		}	
	}

	private void handleException() {
		BusinessExceptionDto businessException = exceptionLoggerService.popBusinessException();
		if(businessException != null){			
			exceptionLoggerService.saveBusinessException(businessException);
			logger.debug("save exception : " + businessException);
		}else{
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
