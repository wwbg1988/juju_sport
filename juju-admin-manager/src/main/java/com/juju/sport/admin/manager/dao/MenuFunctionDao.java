package com.juju.sport.admin.manager.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.admin.manager.mapper.MenuExMapper;
import com.juju.sport.admin.manager.mapper.MenuMapper;
import com.juju.sport.admin.manager.pojo.Menu;
import com.juju.sport.admin.manager.pojo.MenuExample;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.mybatis.MyBatisBaseDao;

@Repository
public class MenuFunctionDao extends MyBatisBaseDao<Menu>{

	@Autowired
	@Getter
	private MenuMapper mapper;
	
	@Autowired
	private MenuExMapper functionMapper;
	
	public List<Menu> findByAdminAccountId(String accountId, String roleId){
		return functionMapper.findByAdminAccountId(accountId, roleId);
	}

	public List<Menu> findAll() {
		MenuExample example = new MenuExample();
		MenuExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause(" menu_group_id, menu_function_name");
		return mapper.selectByExample(example);
	}
}
