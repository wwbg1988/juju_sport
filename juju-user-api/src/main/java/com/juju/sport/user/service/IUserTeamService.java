package com.juju.sport.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.user.dto.TeamDto;

/**
 * 
 * 此类描述的是：
 * 
 * @author: cwftalus@163.com
 * @version: 2015年4月2日 上午11:08:40
 */
@Service
public interface IUserTeamService {
	public String saveOrUpdate(TeamDto teamDto);

	public PageResult<TeamDto> findBy(TeamDto teamDto, PageQuery page);
	
	public List<TeamDto> findBy(TeamDto teamDto);
	
	//public List<TeamListDto> findTeam(TeamDto teamDto, PageQuery page);
	
	public TeamDto findByObj(String teamId);

	public int releaseFright(TeamDto teamDto);

	public java.util.List<TeamDto> findSportId(String userAccountId);

	public java.util.List<TeamDto> findJoinList(String id);

}
