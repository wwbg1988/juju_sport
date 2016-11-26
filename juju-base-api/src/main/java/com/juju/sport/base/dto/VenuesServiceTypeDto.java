package com.juju.sport.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class VenuesServiceTypeDto implements Serializable{

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
    private String venuesInfoId;

	@Getter
	@Setter
    private String serviceTypeId;

	@Getter
	@Setter
    private Date createTime;

	@Getter
	@Setter
    private Date lastUpdateTime;

	@Getter
	@Setter
    private Integer stat;

}