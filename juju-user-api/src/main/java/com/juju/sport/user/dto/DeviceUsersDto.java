package com.juju.sport.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class DeviceUsersDto implements Serializable{

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String userName;

	@Getter
	@Setter
    private String userAccountId;

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
