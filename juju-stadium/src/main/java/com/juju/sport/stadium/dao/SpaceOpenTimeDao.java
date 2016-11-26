package com.juju.sport.stadium.dao;

import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.dto.ParameterDto;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.stadium.dto.SpaceOpenTimeDto;
import com.juju.sport.stadium.mapper.OwnSpacesOpenTimeMapper;
import com.juju.sport.stadium.mapper.SpaceOpenTimeMapper;
import com.juju.sport.stadium.mapper.SpacesOpenTimeMapper;
import com.juju.sport.stadium.pojo.SpaceOpenTime;
import com.juju.sport.stadium.pojo.SpaceOpenTimeExample;

@Repository
public class SpaceOpenTimeDao extends MyBatisBaseDao<SpaceOpenTime>{
	@Autowired
	@Getter
	private SpaceOpenTimeMapper mapper;
	
	@Autowired
	@Getter
	private SpacesOpenTimeMapper openMapper;
	
	@Autowired
	@Getter
	private OwnSpacesOpenTimeMapper ownSpacesOpenTimeMapper;
	
	public List<SpaceOpenTime>findSpace(SpaceOpenTimeDto spaceOpenTimeDto){
		SpaceOpenTimeExample example = new SpaceOpenTimeExample();
		SpaceOpenTimeExample.Criteria criteria = example.createCriteria();
		
		if(!StringUtils.isEmpty(String.valueOf(spaceOpenTimeDto.getOpenWeek()))){//日期判断
			criteria.andOpenWeekEqualTo(spaceOpenTimeDto.getOpenWeek());
		}
		if(!StringUtils.isEmpty(spaceOpenTimeDto.getUserAccountId())){
			criteria.andUserAccountIdEqualTo(spaceOpenTimeDto.getOwnerAccountId());//场馆所有者
		}
		if(!StringUtils.isEmpty(spaceOpenTimeDto.getSpaceId())){
			criteria.andSpaceIdEqualTo(spaceOpenTimeDto.getSpaceId());
		}
		if(!StringUtils.isEmpty(spaceOpenTimeDto.getStat()+"")){
			criteria.andStatEqualTo(spaceOpenTimeDto.getStat());
		}
		
		if(spaceOpenTimeDto.getSpaceIds()!=null && spaceOpenTimeDto.getSpaceIds().size()>0){
			criteria.andSpaceIdIn(spaceOpenTimeDto.getSpaceIds());
		}
		if(spaceOpenTimeDto.getStartTime()!=null){
			criteria.andStartTimeEqualTo(spaceOpenTimeDto.getStartTime());
		}
		if(spaceOpenTimeDto.getEndTime()!=null){
			criteria.andEndTimeEqualTo(spaceOpenTimeDto.getEndTime());
		}
		
		return mapper.selectByExample(example);
	}
	
	public List<SpaceOpenTime> findBy(SpaceOpenTimeDto spaceOpenTimeDto) {
		SpaceOpenTimeExample example = new SpaceOpenTimeExample();
		SpaceOpenTimeExample.Criteria criteria = example.createCriteria();
//		spaceOpenTimeDto.setOpenWeek(Short.valueOf(spaceDto.getSjWeek()));//日期 1 2 3 4 5 6 7 表示
//		spaceOpenTimeDto.setStartTime(DateUtils.parse(spaceDto.getSjTime(),DateUtils.HH));//时间  该时间 必须在配置的范围之内
//		spaceOpenTimeDto.setOwnerAccountId(spaceDto.getOwnerAccountId());
		
		if(!StringUtils.isEmpty(String.valueOf(spaceOpenTimeDto.getOpenWeek()))){//日期判断
			criteria.andOpenWeekEqualTo(spaceOpenTimeDto.getOpenWeek());
		}
		if(!StringUtils.isEmpty(spaceOpenTimeDto.getUserAccountId())){
			criteria.andUserAccountIdEqualTo(spaceOpenTimeDto.getOwnerAccountId());//场馆所有者
		}
		if(!StringUtils.isEmpty(spaceOpenTimeDto.getSpaceId())){
			criteria.andSpaceIdEqualTo(spaceOpenTimeDto.getSpaceId());
		}
//		if(!StringUtils.isEmpty(spaceOpenTimeDto.getStat()+"")){
			criteria.andStatEqualTo(DataStatus.ENABLED);
//		}
		
		if(spaceOpenTimeDto.getSpaceIds()!=null && spaceOpenTimeDto.getSpaceIds().size()>0){
			criteria.andSpaceIdIn(spaceOpenTimeDto.getSpaceIds());
		}
		if(spaceOpenTimeDto.getStartTime()!=null){
			criteria.andStartTimeLessThanOrEqualTo(spaceOpenTimeDto.getStartTime());
			criteria.andEndTimeGreaterThan(spaceOpenTimeDto.getStartTime());
		}
		
		return mapper.selectByExample(example);
	}
	
	public int findCount(SpaceOpenTimeDto spaceOpenTimeDto){
//		SpaceOpenTimeExample example = new SpaceOpenTimeExample();
//		SpaceOpenTimeExample.Criteria criteria = example.createCriteria();
//		criteria.andSpaceIdEqualTo(spaceOpenTimeDto.getSpaceId());
//		criteria.andStatEqualTo(1);
//		criteria.andOpenWeekEqualTo(spaceOpenTimeDto.getOpenWeek());
//		if(spaceOpenTimeDto.getStartTime()!=null){
//			criteria.andStartTimeGreaterThanOrEqualTo(spaceOpenTimeDto.getStartTime());
//		}
//		criteria.
//		if(spaceOpenTimeDto.getEndTime()!=null){
//			criteria.andEndTimeLessThanOrEqualTo(spaceOpenTimeDto.getEndTime());
//		}
//		return mapper.countByExample(example);
		
		
		
		return openMapper.countOpen(spaceOpenTimeDto);
		
		
		
	}
	
	public void updateTime(SpaceOpenTimeDto spaceOpenTimeDto){
//		SpaceOpenTimeExample example = new SpaceOpenTimeExample();
//		SpaceOpenTimeExample.Criteria criteria = example.createCriteria();
//		criteria.andSpaceIdEqualTo(spaceOpenTimeDto.getSpaceId());
//		criteria.andStatEqualTo(1);
//		criteria.andOpenWeekEqualTo(spaceOpenTimeDto.getOpenWeek());
//		SpaceOpenTime spaceOpenTime = new SpaceOpenTime();
//		BeanUtils.copyProperties(spaceOpenTimeDto,spaceOpenTime);
//		mapper.updateByExample(spaceOpenTime, example);
		openMapper.updateOpen(spaceOpenTimeDto);
	}
	public void deleteTime(SpaceOpenTimeDto spaceOpenTimeDto){
		openMapper.deleteOpen(spaceOpenTimeDto);
	}
	public void updatePrice(SpaceOpenTimeDto spaceOpenTimeDto){
		SpaceOpenTime spaceOpenTime = new SpaceOpenTime();
		BeanUtils.copyProperties(spaceOpenTimeDto, spaceOpenTime);
		mapper.updateByPrimaryKey(spaceOpenTime);
	}

	public List<SpaceOpenTime> querySpaceTimePrice(List<ParameterDto> parameterList) {
		List<SpaceOpenTime> resultList = ownSpacesOpenTimeMapper.querySpaceTimePrice(parameterList);
		return resultList;
	}
	
	//根据userAccountId获取所有场地对应数据
	public List<Map> queryOpenSpaceList(String userAccountId){
		List<Map> resultList = ownSpacesOpenTimeMapper.queryOpenSpaceList(userAccountId);
		return resultList;
	}
	
}
