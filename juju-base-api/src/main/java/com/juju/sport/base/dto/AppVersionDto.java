package com.juju.sport.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class AppVersionDto implements Serializable{
	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private Integer versionNum = 0;

	@Getter
	@Setter
    private String versionType;

	@Getter
	@Setter
    private String versionUrl;

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