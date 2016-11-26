package com.juju.sport.user.service;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.user.dto.TeamDto;
import com.juju.sport.user.dto.TeamQuery;

public interface ITeamManagerService {

	PageResult<TeamDto> findTeamByPage(TeamQuery query, PageQuery changeToPageQuery);

	void createTeam(TeamDto dto);

	void delete(String id);
	
	Integer findTeamMemberForOrder(String userAccount,int counts);

	void updateTeam(TeamDto dto);

}
