package com.juju.sport.user.service;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.user.dto.TeamMemberDto;
import com.juju.sport.user.dto.TeamMemberQuery;
import com.juju.sport.user.dto.UsersDto;

public interface ITeamMemberService {

	void create(TeamMemberDto dto);

	PageResult<UsersDto> findUserNotInTeam(TeamMemberQuery query, PageQuery changeToPageQuery);

	RequestResult addUserJoinTeam(String userId, String teamId);

	PageResult<TeamMemberDto> findMemberByTeamId(String teamId, PageQuery pageQuery);

	RequestResult deleteTeamMember(String memberId);

	void updateTeamMember(TeamMemberDto dto);

}
