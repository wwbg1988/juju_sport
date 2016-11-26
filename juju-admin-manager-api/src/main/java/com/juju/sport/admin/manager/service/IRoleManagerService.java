package com.juju.sport.admin.manager.service;

import com.juju.sport.admin.manger.dto.RoleActionDto;
import com.juju.sport.admin.manger.dto.RoleDto;
import com.juju.sport.admin.manger.dto.RoleMenuMappingDto;
import com.juju.sport.common.dto.Pair;
import com.juju.sport.common.model.ListResult;
import com.juju.sport.common.model.RequestResult;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rkzhang on 14-9-25.
 */
@Service
public interface IRoleManagerService {

    public List<RoleDto> findAll();
    
    public ListResult<RoleMenuMappingDto> getRoleMenuByRoleId(String roleId);
    
    public ListResult<RoleActionDto> getRoleActionByRoleId(String roled);

	public RequestResult updateRoleMenu(String roleId, List<String> menuIdList);
	
	ListResult<Pair<String, String>> getRoles();

	public RequestResult updateRoleAction(String roleId, List<String> actionIdList);

	public RequestResult deleteById(String roleId);
}
