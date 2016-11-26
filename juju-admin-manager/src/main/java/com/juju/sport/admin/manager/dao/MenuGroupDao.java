package com.juju.sport.admin.manager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.juju.sport.admin.manager.mapper.MenuGroupExMapper;
import com.juju.sport.admin.manager.mapper.MenuGroupMapper;
import com.juju.sport.admin.manager.pojo.MenuGroup;
import com.juju.sport.admin.manager.pojo.MenuGroupExample;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.mybatis.MyBatisBaseDao;

@Repository
public class MenuGroupDao extends MyBatisBaseDao<MenuGroup>{

	@Autowired
	@Getter
	private MenuGroupMapper mapper;
	
	@Autowired
	private MenuGroupExMapper groupMapper;
	
	public List<MenuGroup> findByAdminAccountId(String accountId, String roleId){
		return groupMapper.findMenuGroupByAdminAccountId(accountId, roleId);
	}

	public List<MenuGroup> findAll() {
		MenuGroupExample example = new MenuGroupExample();
		MenuGroupExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}
	
	public Map<String, String> findAsMap(){
		Map<String, String> map = new HashMap<String, String>();
		List<MenuGroup> groups = findAll();
		if(CollectionUtils.isEmpty(groups)){
			return map;
		}
		for(MenuGroup group : groups){
			map.put(group.getId(), group.getMenuGroupName());
		}
		return map;
	}

	public MenuGroup findByName(String menuGroupName) {
		MenuGroupExample example = new MenuGroupExample();
		MenuGroupExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		criteria.andMenuGroupNameEqualTo(menuGroupName.trim());
		List<MenuGroup> groups = mapper.selectByExample(example);
		return CollectionUtils.isEmpty(groups) ? null : groups.get(0);
	}
	
}
