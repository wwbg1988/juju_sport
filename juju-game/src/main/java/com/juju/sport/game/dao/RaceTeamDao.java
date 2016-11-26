package com.juju.sport.game.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.game.dto.RaceTeamDto;
import com.juju.sport.game.dto.RaceTeamQuery;
import com.juju.sport.game.mapper.RaceTeamExMapper;
import com.juju.sport.game.mapper.RaceTeamMapper;
import com.juju.sport.game.pojo.RaceTeam;
import com.juju.sport.game.pojo.RaceTeamExample;
import com.juju.sport.game.pojo.RaceTeamExample.Criteria;

@Repository
public class RaceTeamDao extends MyBatisBaseDao<RaceTeam>{
	
	@Autowired
	@Getter
	private RaceTeamMapper mapper;
	
	@Autowired
	private RaceTeamExMapper exMapper;

	public List<RaceTeamDto> findRaceTeamByPage(RaceTeamQuery query, PageQuery page) {
	    if(StringUtils.isEmpty(query.getRaceTeamName())){
            query.setRaceTeamName(null);
        }else{
            query.setRaceTeamName("%" + query.getRaceTeamName() + "%");
        }
        
        if(StringUtils.isEmpty(query.getSportType())){
            query.setSportType(null);;
        }
		
		return exMapper.findRaceTeamByPage(query, page);
	}
	
	public Long countRaceTeam(RaceTeamQuery query) {
		
		return exMapper.countRaceTeam(query);
	}
	
	public List<RaceTeamDto> findNotEnterForRaceTeamByPage(RaceTeamQuery query, PageQuery page) {
		if(StringUtils.isEmpty(query.getRaceTeamName())){
			query.setRaceTeamName(null);
		}else{
			query.setRaceTeamName("%" + query.getRaceTeamName() + "%");
		}
		
		if(StringUtils.isEmpty(query.getSportType())){
			query.setSportType(null);;
		}
		
		return exMapper.findNotEnterForRaceTeamByPage(query, page);
	}
	
	public Long countNotEnterForRaceTeam(RaceTeamQuery query) {
		
		return exMapper.countNotEnterForRaceTeam(query);
	}

	public void logicDelete(RaceTeamQuery query) {
		RaceTeam team = new RaceTeam();
		team.setStat(DataStatus.DISABLED);
		RaceTeamExample example = new RaceTeamExample();
		Criteria criteria = example.createCriteria();		
		criteria.andRaceIdEqualTo(query.getRaceId());
		criteria.andTeamIdEqualTo(query.getTeamId());
		mapper.updateByExampleSelective(team, example);
	}

	public long countRaceTeamNotInScoreboard(String raceId) {
		
		return exMapper.countRaceTeamNotInScoreboard(raceId);
	}

	public List<RaceTeamDto> findRaceTeamNotInScoreboard(String raceId, PageQuery page) {
	
		return exMapper.findRaceTeamNotInScoreboard(raceId, page);
	}
	
}
