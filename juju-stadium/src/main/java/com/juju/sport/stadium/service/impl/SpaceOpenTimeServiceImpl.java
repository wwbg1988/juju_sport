package com.juju.sport.stadium.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.common.dto.ParameterDto;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.stadium.dao.SpaceDao;
import com.juju.sport.stadium.dao.SpaceOpenTimeDao;
import com.juju.sport.stadium.dto.SpaceOpenTimeDto;
import com.juju.sport.stadium.pojo.SpaceOpenTime;
import com.juju.sport.stadium.service.ISpaceOpenTimeService;

@Service
public class SpaceOpenTimeServiceImpl implements ISpaceOpenTimeService{
	@Autowired
	private SpaceOpenTimeDao spaceOpenTimeDao;
	
	@Autowired
	private SpaceDao spaceDao;
	
	@Override
	public void saveOrUpdate(SpaceOpenTimeDto spaceOpenTimeDto) {
		SpaceOpenTime spaceOpenTime = BeanUtils.createBeanByTarget(spaceOpenTimeDto, SpaceOpenTime.class);
		if(StringUtils.isEmpty(spaceOpenTime.getId())) {
			spaceOpenTimeDao.insert(spaceOpenTime);
		} else {
			spaceOpenTimeDao.updateByPrimaryKey(spaceOpenTime);
		}
	}

	@Override
	public void delete(SpaceOpenTimeDto spaceOpenTimeDto) {
		SpaceOpenTime spaceOpenTime = BeanUtils.createBeanByTarget(spaceOpenTimeDto, SpaceOpenTime.class);
		if(StringUtils.isEmpty(spaceOpenTime.getId())) {
			spaceOpenTime.setStat(0);
			spaceOpenTimeDao.updateByPrimaryKey(spaceOpenTime);
		}
	}

	@Override
	public List<SpaceOpenTimeDto> findBy(SpaceOpenTimeDto spaceOpenTimeDto) {
		List<SpaceOpenTime> list = spaceOpenTimeDao.findBy(spaceOpenTimeDto);
		List<SpaceOpenTimeDto> results = BeanUtils.createBeanListByTarget(list, SpaceOpenTimeDto.class);
		return results;
	}
	@Override
	public List<SpaceOpenTimeDto> findSpace(SpaceOpenTimeDto spaceOpenTimeDto) {
		List<SpaceOpenTime> list = spaceOpenTimeDao.findSpace(spaceOpenTimeDto);
		List<SpaceOpenTimeDto> results = BeanUtils.createBeanListByTarget(list, SpaceOpenTimeDto.class);
		return results;
	}

	@Override
	public int findCount(SpaceOpenTimeDto spaceOpenTimeDto) {

		return spaceOpenTimeDao.findCount(spaceOpenTimeDto);
	}

	@Override
	public void updateOpenTimeInfoByWeek(SpaceOpenTimeDto spaceOpenTimeDto) {
		 spaceOpenTimeDao.updateTime(spaceOpenTimeDto);
		
	}

	@Override
	public void deleteOpenTimeInfoByWeek(SpaceOpenTimeDto spaceOpenTimeDto) {
		 spaceOpenTimeDao.deleteTime(spaceOpenTimeDto);
		
	}

	@Override
	public void updatePrice(SpaceOpenTimeDto spaceOpenTimeDto) {
		spaceOpenTimeDao.updatePrice(spaceOpenTimeDto);
	}

	@Override
	public List<SpaceOpenTimeDto> querySpaceTimePrice(List<ParameterDto> parameterList) {
		List<SpaceOpenTime> result = spaceOpenTimeDao.querySpaceTimePrice(parameterList);
		List<SpaceOpenTimeDto> dataList = BeanUtils.createBeanListByTarget(result, SpaceOpenTimeDto.class);		
		return dataList;
	}

	@Override
	public List<Map> queryOpenSpaceList(String userAccountId) {
		return spaceOpenTimeDao.queryOpenSpaceList(userAccountId);
	}

}
