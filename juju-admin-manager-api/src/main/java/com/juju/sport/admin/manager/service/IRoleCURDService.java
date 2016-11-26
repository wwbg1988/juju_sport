package com.juju.sport.admin.manager.service;

import org.springframework.stereotype.Service;

import com.juju.sport.admin.manger.dto.RoleDto;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.common.model.Result;

@Service
public interface IRoleCURDService {

	Result<RoleDto> findById(String roleId);
	
	RequestResult create(RoleDto dto);
	
	RequestResult update(RoleDto dto);
	
	RequestResult delete(String roleId);
	
}
