package com.juju.sport.home.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.base.dto.ServiceTypeDto;
import com.juju.sport.base.service.IServiceTypeService;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.Response;

@Controller
@RequestMapping(value = "/serviceType")
public class ServiceTypeController {
	@Autowired
	private IServiceTypeService iServiceTypeService;
	
	@RequestMapping(value = "/list.do")
	@ResponseBody
	public Response<List<ServiceTypeDto>> list(ServiceTypeDto serviceTypeDto) {
		Response<List<ServiceTypeDto>> responses = new Response<List<ServiceTypeDto>>();
		List<ServiceTypeDto> result = iServiceTypeService.findBy(serviceTypeDto);
		responses.setStatus(DataStatus.HTTP_SUCCESS);
		responses.setData(result);
		return responses;
	}
}
