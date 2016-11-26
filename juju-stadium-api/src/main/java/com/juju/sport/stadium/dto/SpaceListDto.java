package com.juju.sport.stadium.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class SpaceListDto implements Serializable{
	
	@Getter
	@Setter
	private SpaceDto spaceDto;
	
	@Getter
	@Setter
	private List<SpaceOpenTimeDto> spaceOpenList;
}
