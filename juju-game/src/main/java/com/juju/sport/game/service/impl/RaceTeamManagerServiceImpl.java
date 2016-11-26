package com.juju.sport.game.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.juju.sport.base.constants.WarType;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.game.dao.RaceInfoDao;
import com.juju.sport.game.dao.RaceScoreboardDao;
import com.juju.sport.game.dao.RaceTeamDao;
import com.juju.sport.game.dto.RaceScoreboardDto;
import com.juju.sport.game.dto.RaceTeamDto;
import com.juju.sport.game.dto.RaceTeamQuery;
import com.juju.sport.game.pojo.RaceInfo;
import com.juju.sport.game.pojo.RaceScoreboard;
import com.juju.sport.game.pojo.RaceTeam;
import com.juju.sport.game.service.IRaceTeamManagerService;
import com.juju.sport.user.dto.TeamDto;
import com.juju.sport.user.service.ITeamService;

@Service
public class RaceTeamManagerServiceImpl implements IRaceTeamManagerService {

	@Autowired
	private RaceTeamDao teamDao;
	
	@Autowired
	private RaceScoreboardDao raceScoreboardDao;
	
	@Autowired
	private ITeamService teamService;
	
	@Autowired
	private RaceInfoDao raceInfoDao;

	@Override
	public PageResult<RaceTeamDto> findRaceTeam(RaceTeamQuery query, PageQuery page) {
		List<RaceTeamDto> results = teamDao.findRaceTeamByPage(query, page);
		setWarTypeName(results);
		long total = teamDao.countRaceTeam(query);
		page.setTotal(total);
		return new PageResult<RaceTeamDto>(page, results);
	}

	@Override
	public PageResult<RaceTeamDto> findNotEnterForRaceTeam(RaceTeamQuery query, PageQuery page) {
		
		List<RaceTeamDto> results = teamDao.findNotEnterForRaceTeamByPage(query, page);
		setWarTypeName(results);
		long total = teamDao.countNotEnterForRaceTeam(query);
		page.setTotal(total);
		return new PageResult<RaceTeamDto>(page, results);
	}

	private void setWarTypeName(List<RaceTeamDto> results) {
		if(!CollectionUtils.isEmpty(results)) {
			for(RaceTeamDto team : results) {
				team.setWarTypeName(WarType.getTypeDesc(team.getWarType()));
			}
		}
	}

	@Override
	public void addRaceTeam(RaceTeamQuery query) {
		RaceTeam raceTeam = new RaceTeam();
		raceTeam.setRaceId(query.getRaceId());
		raceTeam.setTeamId(query.getTeamId());
		teamDao.insert(raceTeam);
	}

	@Override
	public void deleteRaceTeam(RaceTeamQuery query) {
		
		teamDao.logicDelete(query);
	}

	@Override
	public void addRaceScoreboard(RaceTeamQuery query) {
		RaceScoreboard raceScoreboard = new RaceScoreboard();
		TeamDto team = teamService.findByTeamId(query.getTeamId());
		RaceInfo raceInfo = raceInfoDao.selectByPrimaryKey(query.getRaceId());
		raceScoreboard.setRaceInfoId(query.getRaceId());
		raceScoreboard.setTeamId(query.getTeamId());
		if(team != null) {
			raceScoreboard.setTeamName(team.getTeamName());
		}
		if(raceInfo != null) {
			raceScoreboard.setRaceInfoTitle(raceInfo.getTitle());
		}
		raceScoreboardDao.insert(raceScoreboard);
	}

	@Override
	public PageResult<RaceScoreboardDto> findRaceScoreboard(RaceTeamQuery query, PageQuery pageQuery) {
		List<RaceScoreboard> results = raceScoreboardDao.findByRaceId(query.getRaceId(), pageQuery);
		List<RaceScoreboardDto> dtoList = BeanUtils.createBeanListByTarget(results, RaceScoreboardDto.class);
		long total = raceScoreboardDao.countByRaceId(query.getRaceId());
		pageQuery.setTotal(total);
		return new PageResult<RaceScoreboardDto>(pageQuery, dtoList);
	}

	@Override
	public PageResult<RaceTeamDto> findRaceTeamNotInScoreboard(String raceId, PageQuery page) {
		List<RaceTeamDto> dtoList = teamDao.findRaceTeamNotInScoreboard(raceId, page);
		long total = teamDao.countRaceTeamNotInScoreboard(raceId);
		page.setTotal(total);
		return new PageResult<RaceTeamDto>(page, dtoList);
	}

	@Override
	public void updateRaceScoreboard(RaceScoreboardDto scoreboardDto) {
		RaceScoreboard scoreboard = BeanUtils.createBeanByTarget(scoreboardDto, RaceScoreboard.class);
		raceScoreboardDao.updateByPrimaryKey(scoreboard);
	}

	@Override
	public void deleteRaceScoreboard(String id) {
		raceScoreboardDao.logicDeleteRaceScoreboard(id);
	}	

}
