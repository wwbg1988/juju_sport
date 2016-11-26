package com.juju.sport.home.dto.m;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class OrderTimes implements Serializable{
	
	@Getter
	@Setter
	private String orderTime;
	
	@Getter
	@Setter
	private List<SpaceTimeDto> spaceLists;
}
