package com.juju.sport.user.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.UUIDGenerator;
import com.juju.sport.user.dao.UserTeamListDao;
import com.juju.sport.user.dto.TeamDto;
import com.juju.sport.user.dto.TeamListDto;
import com.juju.sport.user.dto.TeamListViewDto;
import com.juju.sport.user.pojo.TeamList;
import com.juju.sport.user.service.IUserTeamListService;

/**
 * 
 * 此类描述的是：
 * 
 * @author: cwftalus@163.com
 * @version: 2015年4月2日 下午1:37:04
 */
@Service
public class UserTeamListServiceImpl implements IUserTeamListService {

	@Autowired
	private UserTeamListDao userTeamListDao;
	
	@Override
	public PageResult<TeamListDto> findBy(TeamListDto teamListDto,PageQuery page) {
		List<TeamList> results = userTeamListDao.findBy(teamListDto, page);
		List<TeamListDto> teams = BeanUtils.createBeanListByTarget(results,TeamDto.class);
//		for (TeamDto team : teams) {
//		}
		long total = userTeamListDao.count(teamListDto);
		page.setTotal(total);
		return new PageResult<TeamListDto>(page, teams);
	}

	@Override
	public List<TeamListDto> findBy(TeamListDto teamListDto) {
		List<TeamList> results = userTeamListDao.findBy(teamListDto);
		List<TeamListDto> teams = BeanUtils.createBeanListByTarget(results,TeamListDto.class);
		return teams;
	}

	@Override
	public String saveOrUpdate(TeamListDto teamListDto) {
		TeamList teamList = BeanUtils.createBeanByTarget(teamListDto, TeamList.class);
		if (StringUtils.isEmpty(teamListDto.getId())) {
			String teamListId = UUIDGenerator.getUUID();
			teamList.setId(teamListId);
			userTeamListDao.insert(teamList);
			return teamListId;
		} else {
			userTeamListDao.updateByPrimaryKey(teamList);
			return "0";
		}
	}

	@Override
	public PageResult<TeamListViewDto> findJoinTeam(TeamListDto teamListDto,PageQuery page) {
		List<TeamListViewDto> teamListViewDto= userTeamListDao.findAllTeam(teamListDto,page);
		long total = userTeamListDao.selectAllTeamList(teamListDto);
		page.setTotal(total);
		return new PageResult<TeamListViewDto>(page, teamListViewDto);
	}
	@Override
	public PageResult<TeamListViewDto> findAllTeam(TeamListDto teamListDto,PageQuery page) {
		List<TeamListViewDto> teamListViewDto= userTeamListDao.findAllTeamByPhone(teamListDto,page);
		long total = userTeamListDao.selectAllTeamByPhone(teamListDto);
		page.setTotal(total);
		return new PageResult<TeamListViewDto>(page, teamListViewDto);
	}
	
	@Override
	public List<TeamListViewDto> showTeamMembers(String id) {
		List<TeamListViewDto> res = userTeamListDao.showTeamMembers(id);
		return res;
	}

	@Override
	public List<TeamListDto> findFootClub(TeamListDto teamListDto) {
		return userTeamListDao.findFootClub(teamListDto);
	}

	@Override
	public List<TeamListViewDto> findTeamsAndSportId(String userAccountId,
			String sportId) {
		return userTeamListDao.findTeamsAndSportId(userAccountId,sportId);
	}
	
	
}
