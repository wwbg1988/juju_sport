package com.juju.sport.admin.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.common.constants.BusinessExceptionDiscription;
import com.juju.sport.common.exception.BusinessException;
import com.juju.sport.common.exception.SystemException;
import com.juju.sport.common.model.RequestResult;

@Controller
@RequestMapping(value="/exception")
public class ExceptionTestColltroller {

	
	@ResponseBody
	@RequestMapping(value="/tbusiness.do")
	public RequestResult throwBussinessException(){
		int a = 1;
		if(a == 1) {
			throw new BusinessException(BusinessExceptionDiscription.USER_ERROR_001);
				
		}
		return new RequestResult(true, "测试结束");
	}
	
	@ResponseBody
	@RequestMapping(value="/tsystem.do")
	public RequestResult throwSystemException(){
		int a = 1;
		if(a == 1) {
			throw new SystemException(BusinessExceptionDiscription.USER_ERROR_001.getMessage());
		}
		return new RequestResult(true, "测试结束");
	}
}
