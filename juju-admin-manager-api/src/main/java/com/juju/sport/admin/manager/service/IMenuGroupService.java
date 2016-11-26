package com.juju.sport.admin.manager.service;

import org.springframework.stereotype.Service;

import com.juju.sport.admin.manger.dto.MenuGroupDto;
import com.juju.sport.common.model.ListResult;

@Service
public interface IMenuGroupService {

	public ListResult<MenuGroupDto> findAll();
}
