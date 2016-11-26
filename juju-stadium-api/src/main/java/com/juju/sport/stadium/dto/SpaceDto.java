package com.juju.sport.stadium.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SpaceDto implements Serializable{
	
	@Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
	private String userAccountId; //用户账号ID
	
	@Getter
	@Setter
	private String ownerAccountId; //场馆账号ID
	
	@Getter
	@Setter
	private String sportId;
	
	@Getter
	@Setter
	private String spaceName;
	
	@Getter
	@Setter
	private Integer price;
	
	@Getter
	@Setter
	private Date createTime;
	
	@Getter
	@Setter
	private Date lastUpdateTime;
	
	@Getter
	@Setter
	private Integer stat;
	
	@Getter
	@Setter
	private String images;
	
	@Getter
	@Setter
	private String orderTime;
	
	@Getter
	@Setter
	private String sjTime;
	
	@Getter
	@Setter
	private String sjWeek;
	
	@Getter
	@Setter
	private String resourceInfos;
	
	@Getter
	@Setter
	private Integer maxNumber;
	
	@Getter
	@Setter
	private Integer minNumber;
	
	

}
