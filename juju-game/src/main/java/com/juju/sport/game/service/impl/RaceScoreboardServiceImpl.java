package com.juju.sport.game.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.game.dao.RaceScoreboardDao;
import com.juju.sport.game.dto.RaceScoreboardDto;
import com.juju.sport.game.pojo.RaceScoreboard;
import com.juju.sport.game.service.IRaceScoreboardService;

@Service
public class RaceScoreboardServiceImpl implements IRaceScoreboardService {

	@Autowired
	private RaceScoreboardDao raceScoreboardDao;
	
	@Override
	public List<RaceScoreboardDto> findBy(String raceId) {
		List<RaceScoreboard> results = raceScoreboardDao.findBy(raceId);
		List<RaceScoreboardDto> dtoResults = BeanUtils.createBeanListByTarget(results, RaceScoreboardDto.class);
		return dtoResults;
	}

}
