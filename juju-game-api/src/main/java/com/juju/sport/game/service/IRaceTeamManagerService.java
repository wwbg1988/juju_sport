package com.juju.sport.game.service;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.game.dto.RaceScoreboardDto;
import com.juju.sport.game.dto.RaceTeamDto;
import com.juju.sport.game.dto.RaceTeamQuery;

public interface IRaceTeamManagerService {

	PageResult<RaceTeamDto> findRaceTeam(RaceTeamQuery query, PageQuery page);
	
	PageResult<RaceTeamDto> findNotEnterForRaceTeam(RaceTeamQuery query, PageQuery page);

	void addRaceTeam(RaceTeamQuery query);
	
	void deleteRaceTeam(RaceTeamQuery query);

	void addRaceScoreboard(RaceTeamQuery query);

	PageResult<RaceScoreboardDto> findRaceScoreboard(RaceTeamQuery query, PageQuery pageQuery);

	PageResult<RaceTeamDto> findRaceTeamNotInScoreboard(String raceId, PageQuery changeToPageQuery);

	void updateRaceScoreboard(RaceScoreboardDto scoreboard);

	void deleteRaceScoreboard(String id);

}
