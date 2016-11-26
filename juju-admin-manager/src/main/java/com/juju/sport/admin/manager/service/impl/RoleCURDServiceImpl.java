package com.juju.sport.admin.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.admin.manager.cache.RoleCache;
import com.juju.sport.admin.manager.dao.RoleDao;
import com.juju.sport.admin.manager.pojo.Role;
import com.juju.sport.admin.manager.service.IRoleCURDService;
import com.juju.sport.admin.manger.dto.RoleDto;
import com.juju.sport.common.exception.SystemException;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.common.model.Result;
import com.juju.sport.common.util.BeanUtils;

@Service
public class RoleCURDServiceImpl implements IRoleCURDService {
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private RoleCache roleCache;

	@Override
	public Result<RoleDto> findById(String roleId) {
		
		return null;
	}

	@Override
	public RequestResult create(RoleDto dto) {
		try{
			Role role = BeanUtils.createBeanByTarget(dto, Role.class);
			roleDao.insert(role);
		}catch(Throwable e){
			throw new SystemException(e);
		}
		return new RequestResult(true, "角色创建成功");
	}

	@Override
	public RequestResult update(RoleDto dto) {
		
		return null;
	}

	@Override
	public RequestResult delete(String roleId) {
		roleDao.logicDelete(roleId);
		return new RequestResult(true, "角色删除成功");
	}

	
}
