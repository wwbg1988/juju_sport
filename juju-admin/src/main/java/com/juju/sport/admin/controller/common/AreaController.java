package com.juju.sport.admin.controller.common;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.base.dto.AddressesDto;
import com.juju.sport.base.service.IAddressesService;
import com.juju.sport.common.model.ListResult;

/**
 * 提供地区数据combo类数据
 * @author rkzhang
 */
@Controller
@RequestMapping(value="/area")
public class AreaController {
	
	@Autowired
	private IAddressesService addressesService;

	@ResponseBody
	@RequestMapping(value="/find.do")
	public ListResult<AddressesDto> find(@Param("parentId")Integer parentId, @Param("level")Integer level) {
		if(level == 1 && parentId == null){
			parentId = 1;
		}
		if(parentId == null) {
			return new ListResult<AddressesDto>(new ArrayList<AddressesDto>());
		}
		return addressesService.findByParentId(parentId);
	}
}
