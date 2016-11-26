package com.juju.sport.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.user.dto.TeamListDto;
import com.juju.sport.user.dto.TeamListViewDto;
import com.juju.sport.user.pojo.TeamList;

public interface TeamListViewMapper {
	List<TeamListViewDto> findAllTeam(@Param("teamListDto")TeamListDto teamListDto, @Param("page")PageQuery page);

	long count(@Param("teamListDto")TeamListDto teamListDto);
	
	List<TeamListViewDto> findAllTeamByPhone(@Param("teamListDto")TeamListDto teamListDto, @Param("page")PageQuery page);
		
	long selectAllTeamByPhone(@Param("teamListDto")TeamListDto teamListDto);

	List<TeamListViewDto> showTeamMembers(@Param("id")String id);
	
	List<TeamList> findFootClub(@Param("teamList")TeamList teamList);

	List<TeamListViewDto> findTeamsAndSportId(@Param("userAccountId")String userAccountId,@Param("sportId")String sportId);

}
