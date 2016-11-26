package com.juju.sport.game.dao;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.base.mapper.SportTypeMapper;
import com.juju.sport.base.pojo.SportType;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.game.dto.RaceInfoDto;
import com.juju.sport.game.dto.RaceInfoQuery;
import com.juju.sport.game.dto.RaceTeamDto;
import com.juju.sport.game.mapper.RaceInfoMapper;
import com.juju.sport.game.mapper.RaceTeamMapper;
import com.juju.sport.game.pojo.RaceInfo;
import com.juju.sport.game.pojo.RaceInfoExample;
import com.juju.sport.game.pojo.RaceInfoExample.Criteria;
import com.juju.sport.game.pojo.RaceTeam;
import com.juju.sport.game.pojo.RaceTeamExample;


@Repository
public class RaceInfoDao extends MyBatisBaseDao<RaceInfo> {
	@Autowired
	@Getter
	private RaceInfoMapper mapper;
	
	@Autowired
	@Getter
	private RaceTeamMapper mapperTeam;
	
	@Autowired
	@Getter
	private SportTypeMapper sportTypeMapper;


	public List<RaceInfo> findByPage(RaceInfoQuery query, PageQuery page) {
		RaceInfoExample example = new RaceInfoExample();
		Criteria criteria = example.createCriteria();
		setQueryParam(query, criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause(" create_time desc limit " + page.getStartNum() + ", " + page.getPageSize());
		return mapper.selectByExample(example);
	}

	public long count(RaceInfoQuery query) {
		RaceInfoExample example = new RaceInfoExample();
		Criteria criteria = example.createCriteria();
		
		setQueryParam(query, criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}
	
	public int logicDeleteById(String id) {
		RaceInfo race = new RaceInfo();
		race.setId(id);
		race.setStat(DataStatus.DISABLED);
		return mapper.updateByPrimaryKeySelective(race);
	}
	
	private void setQueryParam(RaceInfoQuery query, Criteria criteria) {
		if(StringUtils.isNotEmpty(query.getSportTypeId())) {
			criteria.andSportTypeIdEqualTo(query.getSportTypeId());
		}
		
		if(StringUtils.isNotEmpty(query.getTitle())) {
			criteria.andTitleLike("%" + query.getTitle() + "%");
		}
		
		if(StringUtils.isNotEmpty(query.getOrganizers())) {
			criteria.andOrganizersLike("%" + query.getOrganizers() + "%");
		}
	}
	public List<RaceInfoDto> findAll(RaceInfoDto param){
		RaceInfoExample example = new RaceInfoExample();
		RaceInfoExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(1);
		if(param!=null){
		if(!StringUtils.isEmpty(param.getId())){
			criteria.andIdEqualTo(param.getId());
		}
	}	
		List<RaceInfo> list = mapper.selectByExample(example);
		if(list!=null&&list.size()>0){
			List<RaceInfoDto> result = new ArrayList<RaceInfoDto>();
			for(RaceInfo raceInfo:list){
				RaceInfoDto raceInfoDto = new RaceInfoDto();
				BeanUtils.copyProperties(raceInfo, raceInfoDto);
				SportType sportType = sportTypeMapper.selectByPrimaryKey(raceInfoDto.getSportTypeId()+"");
				if(sportType!=null){
					raceInfoDto.setSportName(sportType.getSportName());
				}
				result.add(raceInfoDto);
			}
			return result;
		}
		return null;
	}
	
	public List<RaceTeamDto> findTeamsByRace(RaceTeamDto param){
		RaceTeamExample example = new RaceTeamExample();
		RaceTeamExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(1);
		criteria.andRaceIdEqualTo(param.getRaceId());
		List<RaceTeam> list = mapperTeam.selectByExample(example);
		return BeanUtils.createBeanListByTarget(list, RaceTeamDto.class);
	}
	/**
	 * 
		 * 此方法描述的是：赛事分页信息
		 * @author: cwftalus@163.com
		 * @version: 2015年4月17日 上午9:14:37
	 */
	public List<RaceInfo> findBy(Integer infoType,PageQuery page) {
		RaceInfoExample example = new RaceInfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		
		criteria.andInfoTypeEqualTo(infoType);
		
		example.setOrderByClause(" create_time desc limit " + page.getStartNum() + ", " + page.getPageSize());
		
		return mapper.selectByExample(example);
	}
	/**
	 * 
		 * 此方法描述的是：此方法描述的是：赛事分页信息COUNT
		 * @author: cwftalus@163.com
		 * @version: 2015年5月5日 下午1:46:34
	 */
	public long count(Integer infoType) {
		RaceInfoExample example = new RaceInfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		
		criteria.andInfoTypeEqualTo(infoType);

		return mapper.countByExample(example);
	}

} 
