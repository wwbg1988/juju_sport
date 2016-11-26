package com.juju.sport.admin.manager.mapper;

import java.util.List;

import com.juju.sport.admin.manger.dto.RoleMenuMappingDto;

public interface RoleMenuExMapper {

	List<RoleMenuMappingDto> getRoleMenuByRoleId(String roleId);
}
