package com.juju.sport.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.user.dao.UserTeamDao;
import com.juju.sport.user.dto.TeamDto;
import com.juju.sport.user.pojo.Team;
import com.juju.sport.user.service.ITeamService;

@Service
public class TeamServiceImpl implements ITeamService {

	@Autowired
	private UserTeamDao userTeamDao;
	
	@Override
	public TeamDto findByTeamId(String id) {
		Team team = userTeamDao.selectByPrimaryKey(id);	
		return team == null ? null :  (TeamDto)BeanUtils.createBeanByTarget(team, TeamDto.class);
	}

}
