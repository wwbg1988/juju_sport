package com.juju.sport.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.user.dao.TeamMemberDao;
import com.juju.sport.user.dto.TeamMemberDto;
import com.juju.sport.user.dto.TeamMemberQuery;
import com.juju.sport.user.dto.UsersDto;
import com.juju.sport.user.pojo.TeamMember;
import com.juju.sport.user.service.ITeamMemberService;

@Service
public class TeamMemberServiceImpl implements ITeamMemberService {
	
	@Autowired
	private TeamMemberDao teamMemberDao;

	@Override
	public void create(TeamMemberDto dto) {
		TeamMember member = BeanUtils.createBeanByTarget(dto, TeamMember.class);
		teamMemberDao.insert(member);
	}

	@Override
	public PageResult<UsersDto> findUserNotInTeam(TeamMemberQuery query, PageQuery page) {
		List<UsersDto> results = teamMemberDao.findUserNotInTeam(query, page);
		long total = teamMemberDao.countUserNotInTeam(query);
		page.setTotal(total);
		return new PageResult<UsersDto>(page, results);
	}

	@Override
	public RequestResult addUserJoinTeam(String userId, String teamId) {
		TeamMember teamMember = new TeamMember();
		teamMember.setTeamId(teamId);
		teamMember.setUserId(userId);
		teamMemberDao.insert(teamMember);
		return new RequestResult(true, "添加成功");
	}


	@Override
	public PageResult<TeamMemberDto> findMemberByTeamId(String teamId, PageQuery page) {
		List<TeamMemberDto> results = teamMemberDao.findByTeamId(teamId, page);
		for(TeamMemberDto dto : results) {
			String positionName = TeamMemberDto.TeamPosition.getDescription(dto.getTeamPosition());
			dto.setTeamPositionName(positionName);
		}
		long total = teamMemberDao.countByTeamId(teamId);
		page.setTotal(total);
		return new PageResult<TeamMemberDto>(page, results);
	}

	@Override
	public RequestResult deleteTeamMember(String memberId) {
		teamMemberDao.logicDeleteTeamMember(memberId);
		return new RequestResult(true, "删除成功");
	}

	@Override
	public void updateTeamMember(TeamMemberDto dto) {
		TeamMember member = BeanUtils.createBeanByTarget(dto, TeamMember.class);
		teamMemberDao.updateByPrimaryKeySelective(member);
	}

}
