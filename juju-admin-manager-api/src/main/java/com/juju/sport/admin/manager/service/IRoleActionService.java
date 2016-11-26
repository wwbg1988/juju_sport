package com.juju.sport.admin.manager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.juju.sport.admin.manger.dto.RoleActionDto;
import com.juju.sport.common.model.ListResult;
import com.juju.sport.common.model.RequestResult;

@Service
public interface IRoleActionService {
	
	ListResult<RoleActionDto> getRoleActionByRoleId(String roleId);

	RequestResult updateRoleAction(String roleId, List<String> actionIdList);

	void logicDeleteRoleActionByRoleId(String roleId);
	
}
