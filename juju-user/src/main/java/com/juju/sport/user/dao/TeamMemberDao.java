package com.juju.sport.user.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.user.dto.TeamMemberDto;
import com.juju.sport.user.dto.TeamMemberQuery;
import com.juju.sport.user.dto.UsersDto;
import com.juju.sport.user.mapper.TeamMemberExMapper;
import com.juju.sport.user.mapper.TeamMemberMapper;
import com.juju.sport.user.pojo.TeamMember;

@Repository
public class TeamMemberDao extends MyBatisBaseDao<TeamMember>{

	@Autowired
	@Getter
	private TeamMemberMapper mapper;
	
	@Autowired
	private TeamMemberExMapper exMapper;

	public List<UsersDto> findUserNotInTeam(TeamMemberQuery query, PageQuery page) {		
		processQuery(query);		
		return exMapper.findUserNotInTeam(query, page);
	}

	public long countUserNotInTeam(TeamMemberQuery query) {
		processQuery(query);
		return exMapper.countUserNotInTeam(query);
	}	
	
	
	public Integer findPersonOrder(String userAccount,int minCount){
		if(exMapper.findIsTeam(userAccount)!=0){
			if(exMapper.findIsCap(userAccount)!=0){
				String teamId = exMapper.findTeamByPerson(userAccount);
				if(!StringUtils.isEmpty(teamId)&&!"".equals(teamId)){
					if(exMapper.findTeamCountPerson(teamId)>=minCount){
						return 3;
					}
					return 2;
				}
				return 2;
			}else{
				return 1;
			}
		}else{
			return 0;
		}
	}
	
	
	
	private void processQuery(TeamMemberQuery query) {
		if(StringUtils.isNotEmpty(query.getRealName())) {
			query.setRealName("%" + query.getRealName() + "%");
		} else {
			query.setRealName(null);
		}
		
		if(StringUtils.isNotEmpty(query.getUserAccount())) {
			query.setUserAccount("%" + query.getUserAccount() + "%");
		} else {
			query.setUserAccount(null);
		}
	}

	public List<TeamMemberDto> findByTeamId(String teamId, PageQuery page) {
		
		return exMapper.findByTeamId(teamId, page);
	}

	public long countByTeamId(String teamId) {
	
		return exMapper.countByTeamId(teamId);
	}

	public void logicDeleteTeamMember(String memberId) {
		TeamMember member = new TeamMember();
		member.setId(memberId);
		member.setStat(DataStatus.DISABLED);
		mapper.updateByPrimaryKeySelective(member);
	}

}
