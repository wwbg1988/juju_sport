package com.juju.sport.user.dao;

import java.util.List;

import lombok.Getter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.user.dto.TeamDto;
import com.juju.sport.user.dto.TeamMemberDto;
import com.juju.sport.user.mapper.TeamMapper;
import com.juju.sport.user.mapper.TeamMappers;
import com.juju.sport.user.mapper.TeamMemberExMapper;
import com.juju.sport.user.pojo.Team;
import com.juju.sport.user.pojo.TeamExample;
import com.juju.sport.user.pojo.TeamExample.Criteria;

/**
 * 
	 * 此类描述的是：
	 * @author: cwftalus@163.com
	 * @version: 2015年4月2日 上午11:09:17
 */

@Repository
public class UserTeamDao extends MyBatisBaseDao<Team>{
    @Autowired
    @Getter
    private TeamMapper mapper;
    
    @Autowired
    @Getter
    private TeamMappers mappers;
    
    @Autowired
    @Getter
    private TeamMemberExMapper exMapper;

	public List<Team> findBy(TeamDto teamDto, PageQuery page) {
		TeamExample example = new TeamExample();
		example = queryBySql(teamDto);
		example.setOrderByClause(" create_time desc limit " + page.getStartNum() + ", " + page.getPageSize());
		List<Team> list = mapper.selectByExample(example);
		return list;
	}

	public long count(TeamDto teamDto) {
		TeamExample example = new TeamExample();
		example = queryBySql(teamDto);
				
		return mapper.countByExample(example);
	}
	
	public TeamExample queryBySql(TeamDto teamDto){
		TeamExample example = new TeamExample();
		Criteria criteria = example.createCriteria();
		
		criteria.andStatEqualTo(DataStatus.ENABLED);
		
		if(!StringUtils.isEmpty(teamDto.getUserAccountId())){
			criteria.andUserAccountIdEqualTo(teamDto.getUserAccountId());
		}
		if(!StringUtils.isEmpty(teamDto.getId())){
			criteria.andIdEqualTo(teamDto.getId());
		}
		if(teamDto.getTeamIds()!=null && teamDto.getTeamIds().size()>0){
			criteria.andIdIn(teamDto.getTeamIds());	
		}
		
		return example;
	}

	public List<Team> findBy(TeamDto teamDto) {
		TeamExample example = new TeamExample();
		example = queryBySql(teamDto);
		List<Team> list = mapper.selectByExample(example);
		return list;
	}
	
	public Team findByObj(String teamId){
		return mapper.selectByPrimaryKey(teamId);
	}

	public List<TeamMemberDto> findMembersInfo(String id) {
		/*TeamMemberExample example = new TeamMemberExample();
		com.juju.sport.user.pojo.TeamMemberExample.Criteria criteria = example.createCriteria();
		criteria.andTeamIdEqualTo(id);
		return mapperEx.selectByExample(example);*/
		return exMapper.findByTeamIdNoPage(id);
	}

	public List<Team> findSportId(String userAccountId) {
		TeamExample example = new TeamExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserAccountIdEqualTo(userAccountId);
		return mapper.selectByExample(example);
	}

	public List<Team> findJoinList(String id) {
		return mappers.findJoinList(id);
	}	
}
