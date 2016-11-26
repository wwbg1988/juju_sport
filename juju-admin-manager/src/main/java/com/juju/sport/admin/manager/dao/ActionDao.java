package com.juju.sport.admin.manager.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.admin.manager.mapper.ActionExMapper;
import com.juju.sport.admin.manager.mapper.ActionMapper;
import com.juju.sport.admin.manager.pojo.Action;
import com.juju.sport.admin.manager.pojo.ActionExample;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.mybatis.MyBatisBaseDao;

@Repository
public class ActionDao extends MyBatisBaseDao<Action>{

	@Autowired
	@Getter
	private ActionMapper mapper;
	
	@Autowired
	private ActionExMapper exMapper;

	public List<Action> findAll() {
		ActionExample example = new ActionExample();
		ActionExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}
	
	public List<Action> findByRoleId(String roleId) {		
		return exMapper.getActionByRoleId(roleId);
	}

	public void logicDelete(String actionId) {
		Action action = new Action();
		action.setId(actionId);
		action.setStat(DataStatus.DISABLED);
		ActionExample example = new ActionExample();
		ActionExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(actionId);
		mapper.updateByExample(action, example);
	}
}
