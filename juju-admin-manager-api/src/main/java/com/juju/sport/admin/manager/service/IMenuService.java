package com.juju.sport.admin.manager.service;

import org.springframework.stereotype.Service;

import com.juju.sport.admin.manger.dto.AdminMenuFunctionDto;
import com.juju.sport.common.model.ListResult;
import com.juju.sport.common.model.RequestResult;

@Service
public interface IMenuService {

	ListResult<AdminMenuFunctionDto> findAll();

	RequestResult saveOrUpdate(AdminMenuFunctionDto menu);

	RequestResult deleteMenu(String menuId);
}
