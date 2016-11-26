package com.juju.sport.home.dto.m;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class SpaceList implements Serializable{
	
	@Getter
	@Setter
	private String spaceId;
	
	@Getter
	@Setter
	private String spacePrice;
	
	@Getter
	@Setter
	private String spaceName;
}
