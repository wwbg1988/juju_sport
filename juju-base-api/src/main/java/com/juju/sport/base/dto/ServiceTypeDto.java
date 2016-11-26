package com.juju.sport.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class ServiceTypeDto implements Serializable{

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String serviceName;

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