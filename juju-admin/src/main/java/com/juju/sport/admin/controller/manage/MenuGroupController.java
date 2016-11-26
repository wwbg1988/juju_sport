package com.juju.sport.admin.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.admin.manager.service.IMenuGroupService;
import com.juju.sport.admin.manger.dto.MenuGroupDto;
import com.juju.sport.common.model.ListResult;
import com.juju.sport.common.model.RequestResult;

@Controller
@RequestMapping(value="/menu-group")
public class MenuGroupController {

	@Autowired
	private IMenuGroupService menuGroupService;
	
	@ResponseBody
	@RequestMapping(value="/findAll.do")
    public ListResult<MenuGroupDto> findAll(){
		return menuGroupService.findAll();
	}
	
}
