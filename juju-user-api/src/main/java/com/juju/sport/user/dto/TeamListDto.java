package com.juju.sport.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class TeamListDto implements Serializable{
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String teamId;

	@Getter
	@Setter
	private String userAccountId;

	@Getter
	@Setter
	private String telephone;

	@Getter
	@Setter
	private Integer status=0;

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
	private String teamPosition;

}
