package com.juju.sport.stadium.mapper;

import java.util.List;

import com.juju.sport.stadium.dto.SpaceOpenTimeDto;

public interface SpacesOpenTimeMapper {
	Integer findCheckTime(SpaceOpenTimeDto spaceOpenTimeDto);
	
	Integer findCheckDate(SpaceOpenTimeDto spaceOpenTimeDto);
	
	void insertOpen(SpaceOpenTimeDto spaceOpenTimeDto);
	
	List<SpaceOpenTimeDto> findAllOpen(SpaceOpenTimeDto spaceOpenTimeDto);
	
	void updateOpen(SpaceOpenTimeDto spaceOpenTimeDto);
	
	void deleteOpen(SpaceOpenTimeDto spaceOpenTimeDto);
	
	void deleteAllOpen(SpaceOpenTimeDto spaceOpenTimeDto);
	
	Integer countOpen(SpaceOpenTimeDto spaceOpenTimeDto);
}
