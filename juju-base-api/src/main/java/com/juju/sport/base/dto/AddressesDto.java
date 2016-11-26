package com.juju.sport.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class AddressesDto implements Serializable{

	@Getter
	@Setter
	private Integer id;

	@Getter
	@Setter
	private String parentId;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private Integer level;

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