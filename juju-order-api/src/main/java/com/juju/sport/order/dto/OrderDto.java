package com.juju.sport.order.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


//2015年3月23日 下午3:51:30  by bake
@ToString
public class OrderDto implements Serializable{
	
	@Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
	private String orderNo;
	
	@Getter
	@Setter
	private String telephone;
	
	@Getter
	@Setter
	private Integer orderTotal;
	
	@Getter
	@Setter
	private Integer orderStatus;
	
	@Getter
	@Setter
	private String orderStatusName;
	
	@Getter
	@Setter
	private Integer paymentStatus;
	
	@Getter
	@Setter
	private String paymentStatusName;
	
	@Getter
	@Setter
	private String userAccountId;
	
	@Getter
	@Setter
	private String userAccountIdName;
	
	@Getter
	@Setter
	private String userAccount;
	
	@Getter
	@Setter
	private String userName;
	
	@Getter
	@Setter
	private String ownerAccountId;
	
	@Getter
	@Setter
	private String venuesName;
	
	@Getter
	@Setter
	private Integer stat;
	
	@Getter
	@Setter
	private String statName;
	
	@Getter
	@Setter
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date orderTime;
	
	@Getter
	@Setter
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createTime;
	
	@Getter
	@Setter
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date lastUpdateTime;

}
