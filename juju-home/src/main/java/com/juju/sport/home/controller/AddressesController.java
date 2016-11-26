package com.juju.sport.home.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.base.dto.AddressesDto;
import com.juju.sport.base.service.IAddressesService;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.ListResult;
import com.juju.sport.common.model.Response;

/**
 * 
 * 此类描述的是：adresses 管理
 * 
 * @author: cwftalus@163.com
 * @version: 2015年3月24日 下午1:55:59
 */
@Controller
@RequestMapping(value = "/address")
public class AddressesController {

	protected static final Log logger = LogFactory.getLog(AddressesController.class);

	@Autowired
	private IAddressesService iAddressesService;

	/**
	 * 
	 * 此方法描述的是：根据参数获取参数信息
	 * 
	 * @author: wangxiongdx@163.com
	 * @version: 2015年3月24日 下午2:15:51
	 */
	@RequestMapping(value = "/list.do")
	@ResponseBody
	public Response<List<AddressesDto>> list(AddressesDto addressesDto) {
		Response<List<AddressesDto>> responses = new Response<List<AddressesDto>>();
		responses.setStatus(DataStatus.HTTP_SUCCESS);
		List<AddressesDto> result = iAddressesService.findBy(addressesDto);
		responses.setData(result);
		return responses;
	}
	
	@RequestMapping(value = "/listByParentId.do")
	@ResponseBody
	public Response<List<AddressesDto>> listByParentId(Integer parentId) {
		Response<List<AddressesDto>> responses = new Response<List<AddressesDto>>();
		responses.setStatus(DataStatus.HTTP_SUCCESS);
		ListResult<AddressesDto> result = iAddressesService.findByParentId(parentId);
		responses.setData(result.getResults());
		return responses;
	}
}
