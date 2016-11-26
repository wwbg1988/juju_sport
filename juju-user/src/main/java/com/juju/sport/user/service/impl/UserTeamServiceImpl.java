package com.juju.sport.user.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.UUIDGenerator;
import com.juju.sport.user.dao.UserTeamDao;
import com.juju.sport.user.dto.TeamDto;
import com.juju.sport.user.pojo.Team;
import com.juju.sport.user.service.IUserTeamService;

/**
 * 
 * 此类描述的是：
 * 
 * @author: cwftalus@163.com
 * @version: 2015年4月2日 上午11:08:36
 */
@Service
public class UserTeamServiceImpl implements IUserTeamService {

	@Autowired
	private UserTeamDao userTeamDao;

	@Override
	public String saveOrUpdate(TeamDto teamDto) {
		Team team = BeanUtils.createBeanByTarget(teamDto, Team.class);
		if (StringUtils.isEmpty(teamDto.getId())) {
			String teamId = UUIDGenerator.getUUID();
			team.setId(teamId);
			userTeamDao.insert(team);
			return teamId;
		} else {
			userTeamDao.updateByPrimaryKeySelective(team);
			return "0";
		}
	}

	@Override
	public PageResult<TeamDto> findBy(TeamDto teamDto, PageQuery page) {
		List<Team> results = userTeamDao.findBy(teamDto, page);
		List<TeamDto> teams = BeanUtils.createBeanListByTarget(results,TeamDto.class);
		//		for (TeamDto team : teams) {
		//		}
		long total = userTeamDao.count(teamDto);
		page.setTotal(total);
		return new PageResult<TeamDto>(page, teams);
	}

	@Override
	public List<TeamDto> findBy(TeamDto teamDto) {
		List<Team> results = userTeamDao.findBy(teamDto);
		List<TeamDto> teams = BeanUtils.createBeanListByTarget(results,TeamDto.class);
		return teams;
	}

	public TeamDto findByObj(String teamId){
		Team team = userTeamDao.findByObj(teamId);
		TeamDto teamDto = BeanUtils.createBeanByTarget(team,TeamDto.class);
		return teamDto;
	}

	@Override
	public int releaseFright(TeamDto teamDto) {
		Team team = new  Team();
		BeanUtils.copyProperties(teamDto, team);
		int result = userTeamDao.insert(team);
		return result;
	}

	@Override
	public List<TeamDto> findSportId(String userAccountId) {
		List<Team> teamList = userTeamDao.findSportId(userAccountId);
		List<TeamDto> teamDtoList = BeanUtils.createBeanListByTarget(teamList,TeamDto.class);
		return teamDtoList;
		
	}

	@Override
	public List<TeamDto> findJoinList(String id) {
		List<Team> teamList = userTeamDao.findJoinList(id);
		List<TeamDto> teamDtoList = BeanUtils.createBeanListByTarget(teamList,TeamDto.class);
		return teamDtoList;
	}
}
