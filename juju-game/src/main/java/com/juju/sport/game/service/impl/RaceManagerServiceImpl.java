package com.juju.sport.game.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.base.cache.ISportTypeCache;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.game.dao.RaceInfoDao;
import com.juju.sport.game.dto.RaceInfoDto;
import com.juju.sport.game.dto.RaceInfoQuery;
import com.juju.sport.game.pojo.RaceInfo;
import com.juju.sport.game.service.IRaceManagerService;

@Service
public class RaceManagerServiceImpl implements IRaceManagerService {
 
	@Autowired
	private RaceInfoDao raceInfoDao;
	
	@Autowired
	private ISportTypeCache sportTypeCache;
	
	@Override
	public PageResult<RaceInfoDto> findByPage(RaceInfoQuery query, PageQuery page) {
		List<RaceInfo> results = raceInfoDao.findByPage(query, page);
		List<RaceInfoDto> dtoResults = BeanUtils.createBeanListByTarget(results, RaceInfoDto.class);
		for(RaceInfoDto dto : dtoResults){
			if(StringUtils.isNotEmpty(dto.getSportTypeId())) {
				dto.setSportName(sportTypeCache.getNameStr(dto.getSportTypeId()));
			}
			if(StringUtils.isEmpty(dto.getContext())) {
				dto.setContext("");
			}
		}
		long total = raceInfoDao.count(query);
		page.setTotal(total);
		return new PageResult<RaceInfoDto>(page, dtoResults);
	}

	@Override
	public void save(RaceInfoDto raceInfoDto) {
		RaceInfo raceInfo = BeanUtils.createBeanByTarget(raceInfoDto, RaceInfo.class);
		raceInfoDao.insert(raceInfo);
	}

	@Override
	public void deleteById(String id) {
		
		raceInfoDao.logicDeleteById(id);
	}

	@Override
	public void update(RaceInfoDto raceInfoDto) {
		RaceInfo raceInfo = BeanUtils.createBeanByTarget(raceInfoDto, RaceInfo.class);
		raceInfoDao.updateByPrimaryKeySelective(raceInfo);
	}

	@Override
	public int updateContext(RaceInfoDto raceInfo) {
		RaceInfo info = BeanUtils.createBeanByTarget(raceInfo, RaceInfo.class);
		return raceInfoDao.updateByPrimaryKeySelective(info);
	}

}
