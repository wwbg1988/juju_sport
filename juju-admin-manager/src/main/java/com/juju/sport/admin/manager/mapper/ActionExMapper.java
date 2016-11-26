package com.juju.sport.admin.manager.mapper;

import java.util.List;

import com.juju.sport.admin.manager.pojo.Action;

public interface ActionExMapper {

	List<Action> getActionByRoleId(String roleId);
}
