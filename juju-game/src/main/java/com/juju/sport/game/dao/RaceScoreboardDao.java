package com.juju.sport.game.dao;

import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.game.mapper.RaceScoreboardMapper;
import com.juju.sport.game.pojo.RaceScoreboard;
import com.juju.sport.game.pojo.RaceScoreboardExample;
import com.juju.sport.game.pojo.RaceScoreboardExample.Criteria;

@Repository
public class RaceScoreboardDao extends MyBatisBaseDao<RaceScoreboard> {

	@Getter
	@Autowired
	private RaceScoreboardMapper mapper;

	public List<RaceScoreboard> findByRaceId(String raceId, PageQuery page) {
		RaceScoreboardExample example = new RaceScoreboardExample();
		Criteria criteria = example.createCriteria();
		criteria.andRaceInfoIdEqualTo(raceId);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause(" team_group, create_time desc limit " + page.getStartNum() + ", " + page.getPageSize());
		return mapper.selectByExample(example);
	}
	
	public long countByRaceId(String raceId) {
		RaceScoreboardExample example = new RaceScoreboardExample();
		Criteria criteria = example.createCriteria();
		criteria.andRaceInfoIdEqualTo(raceId);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}

	public void logicDeleteRaceScoreboard(String id) {
		RaceScoreboard scoreboard = new RaceScoreboard();
		scoreboard.setId(id);
		scoreboard.setStat(DataStatus.DISABLED);
		mapper.updateByPrimaryKeySelective(scoreboard);
	}


	public List<RaceScoreboard> findBy(String raceId) {
		RaceScoreboardExample example = new RaceScoreboardExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		
		criteria.andRaceInfoIdEqualTo(raceId);
		
		example.setOrderByClause(" team_group asc");
		
		return mapper.selectByExample(example);
	}
}
