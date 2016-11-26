package com.juju.sport.stadium.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class SpaceOpenTimeDto implements Serializable{
	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String spaceId;

	@Getter
	@Setter
    private Short openWeek;

	@Getter
	@Setter
	private String ownerAccountId;
	
	@Getter
	@Setter
    private Date startTime;

	@Getter
	@Setter
    private Date endTime;
	
	@Getter
	@Setter
    private List<String> spaceIds;
	
	@Getter
	@Setter
	private Integer isUser = 0;// 1 可用 0 不可用 2已经使用
	
	@Getter
	@Setter
    private Integer startHour;

	@Getter
	@Setter
    private Integer endHour;
	
	@Getter
	@Setter
	private String openWeekRemark;
	
	@Getter
	@Setter 
	private Short endOpenWeek;
	
	@Getter
	@Setter
	private String endOpenWeekRemark;
	
	@Getter
	@Setter
	private Date createTime;
	
	@Getter
	@Setter
	private Date lastUpdateTime;
	
	@Getter
	@Setter
	private int stat;
	
	@Getter
	@Setter
	private String userAccountId;
	
	@Getter
	@Setter
	private int price;
	
}