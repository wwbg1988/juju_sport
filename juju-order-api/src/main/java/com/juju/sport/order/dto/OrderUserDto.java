package com.juju.sport.order.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class OrderUserDto implements Serializable{
	@Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
	private String userAccountId;
	
	@Getter
	@Setter
	private String orderId;
	
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
	private String ordersType;
}
