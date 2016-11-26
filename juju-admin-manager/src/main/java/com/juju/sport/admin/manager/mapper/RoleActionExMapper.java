package com.juju.sport.admin.manager.mapper;

import java.util.List;

import com.juju.sport.admin.manger.dto.RoleActionDto;

public interface RoleActionExMapper {

	List<RoleActionDto> getRoleActionByRoleId(String roleId);

}
