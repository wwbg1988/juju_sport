package com.juju.sport.stadium.mapper;

import com.juju.sport.stadium.dto.SpaceDto;


public interface SpacesMapper {
	void updateSpace(SpaceDto spaceDto);
	void deleteSpace(SpaceDto spaceDto);
	int findCountSpaceById(SpaceDto spaceDto);
}