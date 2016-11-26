package com.juju.sport.admin.manager.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.admin.manager.mapper.RoleActionExMapper;
import com.juju.sport.admin.manager.mapper.RoleActionMapper;
import com.juju.sport.admin.manager.pojo.RoleAction;
import com.juju.sport.admin.manager.pojo.RoleActionExample;
import com.juju.sport.admin.manger.dto.RoleActionDto;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.mybatis.MyBatisBaseDao;

@Repository
public class RoleActionDao extends MyBatisBaseDao<RoleAction>{

	@Autowired
	@Getter
	private RoleActionMapper mapper;
	
	@Autowired
	private RoleActionExMapper exMapper;

	public List<RoleActionDto> getRoleActionByRoleId(String roleId) {
		return exMapper.getRoleActionByRoleId(roleId);
	}

	public int logicDeleteRoleActionByRoleId(String roleId) {
		RoleAction action = new RoleAction();
		action.setRoleId(roleId);
		action.setStat(DataStatus.DISABLED);
		RoleActionExample example = new RoleActionExample();
		RoleActionExample.Criteria criteria = example.createCriteria();
		criteria.andRoleIdEqualTo(roleId);
		return mapper.updateByExampleSelective(action, example);
	}
	
}
