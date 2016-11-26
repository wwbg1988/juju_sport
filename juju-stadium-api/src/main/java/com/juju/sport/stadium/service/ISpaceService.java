package com.juju.sport.stadium.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.stadium.dto.SpaceDto;
import com.juju.sport.stadium.dto.SpaceListDto;
import com.juju.sport.stadium.dto.SpaceOpenTimeDto;

@Service
public interface ISpaceService {
	public String saveOrUpdate(SpaceDto spaceDto);

	public void delete(SpaceDto spaceDto);
	
	public List<SpaceDto> findBy(SpaceDto spaceDto);
	
	public int findById(SpaceOpenTimeDto spaceOpenTimeDto);
	
	public int findCheckDate(SpaceOpenTimeDto spaceOpenTimeDto);
	
	public void saveOrUpdateOpenTime(SpaceOpenTimeDto spaceOpenTimeDto);
	
	public PageResult<SpaceListDto> findAll(SpaceDto spaceDto,PageQuery page);
	
	public void deleteOnTime(SpaceOpenTimeDto spaceOpenTimeDto);
	
	public SpaceListDto findUpdateInfos(SpaceDto spaceDto);
	
	public void updateSpaceInfos(SpaceDto spaceDto);
	
	public void deleteSpaceInfos(SpaceDto spaceDto);
	
	public int findCountByName(SpaceDto spaceDto);

	public List<SpaceDto> findBy(List<String> spaceIds,String ownerAccountId);
	
	public List<SpaceDto> findSpacesBy(List<String> userAccountIds);
	
}
