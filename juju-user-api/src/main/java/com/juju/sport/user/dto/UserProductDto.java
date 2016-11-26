package com.juju.sport.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class UserProductDto implements Serializable{

	@Setter
	@Getter
    private String id;

	@Setter
	@Getter
    private String scoreProductId;

	@Setter
	@Getter
    private String productName;

	@Setter
	@Getter
    private String userAccountId;

	@Setter
	@Getter
    private String deliveryNo;

	@Setter
	@Getter
    private Integer status;

	@Setter
	@Getter
    private Date createTime;

	@Setter
	@Getter
    private Date lastUpdateTime;

	@Setter
	@Getter
    private Integer stat;

}
