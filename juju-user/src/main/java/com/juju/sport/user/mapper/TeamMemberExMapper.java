package com.juju.sport.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.user.dto.TeamMemberDto;
import com.juju.sport.user.dto.TeamMemberQuery;
import com.juju.sport.user.dto.UsersDto;

public interface TeamMemberExMapper {

	List<UsersDto> findUserNotInTeam(@Param("query")TeamMemberQuery query, @Param("page")PageQuery page);

	long countUserNotInTeam(@Param("query")TeamMemberQuery query);

	List<TeamMemberDto> findByTeamId(@Param("teamId")String teamId, @Param("page")PageQuery page);
	
	List<TeamMemberDto> findByTeamIdNoPage(@Param("teamId")String teamId);

	long countByTeamId(@Param("teamId")String teamId);
	
    Integer findIsTeam(@Param("userAccountId") String userAccountId);
    
    Integer findIsCap(@Param("userAccountId") String userAccountId);
    
    String findTeamByPerson(@Param("userAccountId") String userAccountId);
    
    Integer findTeamCountPerson(@Param("teamId") String teamId);

}
