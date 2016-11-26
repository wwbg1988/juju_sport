package com.juju.sport.home.dto.m;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class SpaceTimeDto implements Serializable{

	@Getter
	@Setter
	private String userAccountId;
	
	@Getter
	@Setter
	private String spaceName;
	
	@Getter
	@Setter
	private String spaceId;
	
	@Getter
	@Setter
	private String openWeek;
	
	@Getter
	@Setter
	private String price;
	
	@Getter
	@Setter
	private String startTime;
	
	@Getter
	@Setter
	private String endTime;
	
	@Getter
	@Setter
	private String orderTime;
}
