package com.juju.sport.game.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.game.dto.RaceTeamDto;
import com.juju.sport.game.dto.RaceTeamQuery;

public interface RaceTeamExMapper {

	List<RaceTeamDto> findRaceTeamByPage(@Param("query")RaceTeamQuery query, @Param("page")PageQuery page);
	
	Long countRaceTeam(@Param("query")RaceTeamQuery query);
	
	List<RaceTeamDto> findNotEnterForRaceTeamByPage(@Param("query")RaceTeamQuery query, @Param("page")PageQuery page);
	
	Long countNotEnterForRaceTeam(@Param("query")RaceTeamQuery query);

	Long countRaceTeamNotInScoreboard(@Param("raceId")String raceId);

	List<RaceTeamDto> findRaceTeamNotInScoreboard(@Param("raceId")String raceId, @Param("page")PageQuery page);	
	
}
