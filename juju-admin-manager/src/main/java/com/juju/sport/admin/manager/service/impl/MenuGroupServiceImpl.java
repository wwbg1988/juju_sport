package com.juju.sport.admin.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.admin.manager.dao.MenuGroupDao;
import com.juju.sport.admin.manager.pojo.MenuGroup;
import com.juju.sport.admin.manager.service.IMenuGroupService;
import com.juju.sport.admin.manger.dto.MenuGroupDto;
import com.juju.sport.common.model.ListResult;
import com.juju.sport.common.util.BeanUtils;

@Service
public class MenuGroupServiceImpl implements IMenuGroupService {

	@Autowired
	private MenuGroupDao groupDao;
	
	@Override
	public ListResult<MenuGroupDto> findAll() {
		List<MenuGroup> groups = groupDao.findAll();
		List<MenuGroupDto> results = BeanUtils.createBeanListByTarget(groups, MenuGroupDto.class);
		return new ListResult<MenuGroupDto>(results);
	}

}
