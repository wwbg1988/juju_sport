package com.juju.sport.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.user.dto.TeamListDto;
import com.juju.sport.user.dto.TeamListViewDto;

/**
 * 
 * 此类描述的是：战队成员列表
 * 
 * @author: cwftalus@163.com
 * @version: 2015年4月2日 下午1:34:45
 */
@Service
public interface IUserTeamListService {
	public PageResult<TeamListDto> findBy(TeamListDto teamListDto, PageQuery page);
	
	public List<TeamListDto> findBy(TeamListDto teamListDto);

	public String saveOrUpdate(TeamListDto teamListDto);

	//查询能加入的战队
	public PageResult<TeamListViewDto> findJoinTeam(TeamListDto teamListDto,PageQuery page);
	
	//手机端查询所有战队
	public PageResult<TeamListViewDto> findAllTeam(TeamListDto teamListDto,PageQuery page);
	
    //查询战队成员
	public List<TeamListViewDto> showTeamMembers(String id);

	
	public List<TeamListDto> findFootClub(TeamListDto teamListDto);

	public List<TeamListViewDto> findTeamsAndSportId(String userAccountId, String sportId);

}
