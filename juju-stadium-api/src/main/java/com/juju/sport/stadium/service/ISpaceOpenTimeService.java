package com.juju.sport.stadium.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.juju.sport.common.dto.ParameterDto;
import com.juju.sport.stadium.dto.SpaceOpenTimeDto;

@Service
public interface ISpaceOpenTimeService {
	public void saveOrUpdate(SpaceOpenTimeDto spaceOpenTimeDto);

	public void delete(SpaceOpenTimeDto spaceOpenTimeDto);
	
	public List<SpaceOpenTimeDto> findBy(SpaceOpenTimeDto spaceOpenTimeDto);
	
	public List<SpaceOpenTimeDto>findSpace(SpaceOpenTimeDto spaceOpenTimeDto);
	
	int findCount(SpaceOpenTimeDto spaceOpenTimeDto);
	
	void updateOpenTimeInfoByWeek(SpaceOpenTimeDto spaceOpenTimeDto);
	
	void deleteOpenTimeInfoByWeek(SpaceOpenTimeDto spaceOpenTimeDto);
	
	void updatePrice(SpaceOpenTimeDto spaceOpenTimeDto);

	public List<SpaceOpenTimeDto> querySpaceTimePrice(List<ParameterDto> parameterList);

	public List<Map> queryOpenSpaceList(String ownerAccountId);
}
