package com.juju.sport.game.service;

import java.util.List;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.game.dto.RaceInfoDto;
import com.juju.sport.game.dto.RaceListDto;

public interface IRaceService {
	public List<RaceInfoDto> findAll();
	
	public RaceListDto findDetails(RaceInfoDto raceInfo);

	public PageResult<RaceInfoDto> findBy(Integer infoType,PageQuery page);
}
