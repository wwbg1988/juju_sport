package com.juju.sport.game.service;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.game.dto.RaceInfoDto;
import com.juju.sport.game.dto.RaceInfoQuery;

public interface IRaceManagerService {

	PageResult<RaceInfoDto> findByPage(RaceInfoQuery query, PageQuery page);

	void save(RaceInfoDto raceInfo);

	void deleteById(String id);

	void update(RaceInfoDto raceInfo);

	int updateContext(RaceInfoDto raceInfo);

}
