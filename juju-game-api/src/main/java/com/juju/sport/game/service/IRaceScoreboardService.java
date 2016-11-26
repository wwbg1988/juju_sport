package com.juju.sport.game.service;

import java.util.List;

import com.juju.sport.game.dto.RaceScoreboardDto;

public interface IRaceScoreboardService {

	List<RaceScoreboardDto> findBy(String raceId);

}
