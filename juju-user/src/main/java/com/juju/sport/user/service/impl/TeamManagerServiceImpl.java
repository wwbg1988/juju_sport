package com.juju.sport.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.juju.sport.base.constants.WarType;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.user.dao.TeamDao;
import com.juju.sport.user.dao.TeamMemberDao;
import com.juju.sport.user.dto.TeamDto;
import com.juju.sport.user.dto.TeamQuery;
import com.juju.sport.user.pojo.Team;
import com.juju.sport.user.service.ITeamManagerService;

@Service
public class TeamManagerServiceImpl implements ITeamManagerService {
	
	@Autowired
	private TeamDao teamDao;
	
	@Autowired
	private TeamMemberDao teamMemberDao;

	@Override
	public PageResult<TeamDto> findTeamByPage(TeamQuery query, PageQuery page) {
		List<TeamDto> teams = teamDao.findTeamByPage(query, page);
		setWarTypeName(teams);
		long total = teamDao.countTeam(query);
		page.setTotal(total);
		return new PageResult<TeamDto>(page, teams);
	}
	
	private void setWarTypeName(List<TeamDto> results) {
		if(!CollectionUtils.isEmpty(results)) {
			for(TeamDto team : results) {
				team.setWarTypeName(WarType.getTypeDesc(team.getWarType()));
			}
		}
	}

	@Override
	public void createTeam(TeamDto dto) {
		Team team = BeanUtils.createBeanByTarget(dto, Team.class);
		teamDao.insert(team);	
	}

	@Override
	public void delete(String id) {
		teamDao.logicDeleteById(id);
	}

	@Override
	public Integer findTeamMemberForOrder(String userAccount,int counts) {
		return teamMemberDao.findPersonOrder(userAccount,counts);
	}

	@Override
	public void updateTeam(TeamDto dto) {
		Team team = BeanUtils.createBeanByTarget(dto, Team.class);
		teamDao.updateByPrimaryKeySelective(team);
	}

}
