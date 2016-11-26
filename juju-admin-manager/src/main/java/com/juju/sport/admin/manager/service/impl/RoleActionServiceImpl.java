package com.juju.sport.admin.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.sport.admin.manager.dao.RoleActionDao;
import com.juju.sport.admin.manager.pojo.RoleAction;
import com.juju.sport.admin.manager.service.IRoleActionService;
import com.juju.sport.admin.manger.dto.RoleActionDto;
import com.juju.sport.common.model.ListResult;
import com.juju.sport.common.model.RequestResult;

@Service
public class RoleActionServiceImpl implements IRoleActionService {

	@Autowired
	private RoleActionDao roleActionDao;
	
	@Transactional
	public RequestResult updateRoleAction(String roleId, List<String> actionIdList) {
		roleActionDao.logicDeleteRoleActionByRoleId(roleId);
		for(String actionId : actionIdList) {
			RoleAction roleAction = new RoleAction();
			roleAction.setRoleId(roleId);
			roleAction.setActionId(actionId);
			roleActionDao.insert(roleAction);
		}
		return new RequestResult(true, "更新功能权限成功！");
	}

	@Override
	public ListResult<RoleActionDto> getRoleActionByRoleId(String roleId) {
		List<RoleActionDto> roleActions = roleActionDao.getRoleActionByRoleId(roleId);
		return new ListResult<>(roleActions);
	}

	@Override
	public void logicDeleteRoleActionByRoleId(String roleId) {
		roleActionDao.logicDeleteRoleActionByRoleId(roleId);
		
	}

}
