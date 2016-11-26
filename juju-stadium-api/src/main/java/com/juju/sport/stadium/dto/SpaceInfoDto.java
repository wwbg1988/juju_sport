package com.juju.sport.stadium.dto;

import java.io.Serializable;

import com.juju.sport.common.constants.DataStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class SpaceInfoDto implements Serializable{
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String spaceId;

	@Getter
	@Setter
	private String openWeek;

	@Getter
	@Setter
	private String startTime;

	@Getter
	@Setter
	private String endTime;

	@Getter
	@Setter
	private Integer isUser = DataStatus.DISABLED;// 1 可用 0 不可用 2已经使用

	@Getter
	@Setter
	private Integer price = 0;// 0元 免费
	
	@Getter
	@Setter
	private String hour;
	
	@Getter
	@Setter
	private String startHour;

	@Getter
	@Setter
	private String endHour;

}
