package com.juju.sport.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class TeamListViewDto implements Serializable{
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
	private String teamName;

	@Getter
	@Setter
	private Integer warType;

	@Getter
	@Setter
	private Integer joinNum;

	@Getter
	@Setter
	private Integer MaxNum;
	
	@Getter
	@Setter
	private String thumbnail;
	
	@Getter
	@Setter
	private String sportId;
	
	@Getter
	@Setter
	private String sportName;
	
	@Getter
	@Setter
	private String warDesc;
	
	@Getter
	@Setter
	private String nickName;
	
	@Getter
	@Setter
	private String teamPosition;
	
	@Getter
	@Setter
	private String userImage;
}
