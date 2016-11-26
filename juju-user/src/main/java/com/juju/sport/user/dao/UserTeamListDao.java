package com.juju.sport.user.dao;

import java.util.List;

import lombok.Getter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.user.dto.TeamListDto;
import com.juju.sport.user.dto.TeamListViewDto;
import com.juju.sport.user.mapper.TeamListMapper;
import com.juju.sport.user.mapper.TeamListViewMapper;
import com.juju.sport.user.pojo.TeamList;
import com.juju.sport.user.pojo.TeamListExample;
import com.juju.sport.user.pojo.TeamListExample.Criteria;

/**
 * 
	 * 此类描述的是：
	 * @author: cwftalus@163.com
	 * @version: 2015年4月2日 上午11:09:17
 */

@Repository
public class UserTeamListDao extends MyBatisBaseDao<TeamList>{
    @Autowired
    @Getter
    private TeamListMapper mapper;
    @Autowired
    @Getter
    private TeamListViewMapper ExMapper;

	public List<TeamList> findBy(TeamListDto teamListDto, PageQuery page) {
		TeamListExample example = queryBySql(teamListDto);
		example.setOrderByClause(" create_time desc limit " + page.getStartNum() + ", " + page.getPageSize());
		List<TeamList> list = mapper.selectByExample(example);
		return list;
	}

	public long count(TeamListDto teamListDto) {
		TeamListExample example = queryBySql(teamListDto);
				
		return mapper.countByExample(example);
	}
	
	public TeamListExample queryBySql(TeamListDto teamListDto){
		TeamListExample example = new TeamListExample();
		Criteria criteria = example.createCriteria();
		
		criteria.andStatEqualTo(DataStatus.ENABLED);
		if(!StringUtils.isEmpty(teamListDto.getUserAccountId())){
			criteria.andUserAccountIdEqualTo(teamListDto.getUserAccountId());
		}
		if(!StringUtils.isEmpty(teamListDto.getTeamId())){
			criteria.andTeamIdEqualTo(teamListDto.getTeamId());	
		}
		if(!StringUtils.isEmpty(teamListDto.getTeamPosition())){
			criteria.andTeamPositionEqualTo(teamListDto.getTeamPosition());
		}
		return example;
	}

	public List<TeamList> findBy(TeamListDto teamListDto) {
		TeamListExample example = queryBySql(teamListDto);
		List<TeamList> list = mapper.selectByExample(example);
		return list;
	}
	
	public List<TeamListDto> findFootClub(TeamListDto teamListDto){
		TeamList params = new TeamList();
		BeanUtils.copyProperties(teamListDto, params);
		List<TeamList> list = ExMapper.findFootClub(params);
		if(list!=null&&list.size()>0){
			List<TeamListDto> result=BeanUtils.createBeanListByTarget(list, TeamListDto.class);
			return result;
		}
		return null;
	}

	public List<TeamListViewDto> findAllTeam(TeamListDto teamListDto, PageQuery page) {
		return ExMapper.findAllTeam(teamListDto,page);
		
	}

	public long selectAllTeamList(TeamListDto teamListDto) {
		return ExMapper.count(teamListDto);
	}
	
	public List<TeamListViewDto> findAllTeamByPhone(TeamListDto teamListDto, PageQuery page) {
		return ExMapper.findAllTeamByPhone(teamListDto,page);
		
	}

	public long selectAllTeamByPhone(TeamListDto teamListDto) {
		return ExMapper.selectAllTeamByPhone(teamListDto);
	}

	public List<TeamListViewDto> showTeamMembers(String id) {
		return ExMapper.showTeamMembers(id);
	}

	public List<TeamListViewDto> findTeamsAndSportId(String userAccountId,
			String sportId) {
		return ExMapper.findTeamsAndSportId(userAccountId,sportId);
	}
	
	
	
}
