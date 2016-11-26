package com.juju.sport.user.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.user.dto.TeamDto;
import com.juju.sport.user.dto.TeamQuery;
import com.juju.sport.user.mapper.TeamExMapper;
import com.juju.sport.user.mapper.TeamMapper;
import com.juju.sport.user.pojo.Team;

@Repository
public class TeamDao extends MyBatisBaseDao<Team> {

	@Autowired
	@Getter
	private TeamMapper mapper;
	
	@Autowired
	private TeamExMapper teamExMapper;

	public List<TeamDto> findTeamByPage(TeamQuery query, PageQuery page) {
		setQuery(query);
		return teamExMapper.findTeamByPage(query, page);
	}
	
	public long countTeam(TeamQuery query) {
		setQuery(query);
		return teamExMapper.countTeam(query);
	}
	
	private void setQuery(TeamQuery query) {
		if(StringUtils.isEmpty(query.getTeamName())){
			query.setTeamName(null);
		} else {
			query.setTeamName("%" + query.getTeamName() + "%");
		}
		if(StringUtils.isEmpty(query.getSportType())){
			query.setSportType(null);
		}
	}

	public void logicDeleteById(String id) {
		Team team = new Team();
		team.setId(id);
		team.setStat(DataStatus.DISABLED);
		mapper.updateByPrimaryKeySelective(team);
	}

}
