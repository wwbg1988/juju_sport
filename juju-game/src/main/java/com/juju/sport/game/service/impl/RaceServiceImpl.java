package com.juju.sport.game.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.game.dao.RaceInfoDao;
import com.juju.sport.game.dto.RaceInfoDto;
import com.juju.sport.game.dto.RaceListDto;
import com.juju.sport.game.dto.RaceTeamDto;
import com.juju.sport.game.pojo.RaceInfo;
import com.juju.sport.game.service.IRaceService;

@Service
public class RaceServiceImpl implements IRaceService{

	@Autowired
	private RaceInfoDao raceDao;
	
	@Override
	public List<RaceInfoDto> findAll() {
		RaceInfoDto raceInfoDto = null;
		return raceDao.findAll(raceInfoDto);
	}
	
	@Override
	public RaceListDto findDetails(RaceInfoDto raceInfoDto) {
		RaceListDto result = new RaceListDto();
		List<RaceInfoDto> list = raceDao.findAll(raceInfoDto);
		RaceInfoDto raceInfoDtos = null;
		if(list!=null&&list.size()>0){
			raceInfoDtos = list.get(0);
			result.setRaceInfoDto(raceInfoDtos);
			RaceTeamDto raceTeamDto = new RaceTeamDto();
			raceTeamDto.setRaceId(raceInfoDto.getId());
			result.setRaceTeamListDto(raceDao.findTeamsByRace(raceTeamDto));
			return result;
		}else{
			return null;
		}
	}
	
	/*
	 * (non-Javadoc)
		 * @see com.juju.sport.game.service.IRaceService#findBy()
	 */
	@Override
	public PageResult<RaceInfoDto> findBy(Integer infoType,PageQuery page) {
		List<RaceInfo> results = raceDao.findBy(infoType,page);
		List<RaceInfoDto> dtoResults = BeanUtils.createBeanListByTarget(results, RaceInfoDto.class);
		long total = raceDao.count(infoType);
		page.setTotal(total);
		return new PageResult<RaceInfoDto>(page, dtoResults);
	}

}
