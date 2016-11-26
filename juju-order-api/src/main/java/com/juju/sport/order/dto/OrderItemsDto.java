package com.juju.sport.order.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;


@ToString
public class OrderItemsDto implements Serializable{
	@Setter
	@Getter
	private String id;

	@Setter
	@Getter
	private String orderId;

	@Setter
	@Getter
	private String orderNo;

	@Setter
	@Getter
	private String date;

	@Setter
	@Getter
	private String orderTime;
	
	@Setter
	@Getter
	private String endTime;

	@Setter
	@Getter
	private String spaceId;

	@Setter
	@Getter
	private String spaceName;

	@Setter
	@Getter
	private String userAccountId;

	@Setter
	@Getter
	private String ownerAccountId;

	@Setter
	@Getter
	private String telephone;

	@Setter
	@Getter
	private Integer orderTotal;
	
	@Setter
    @Getter
    private Integer usrType;

	@Setter
	@Getter
	private Date createTime;

	@Setter
	@Getter
	private Date lastUpdateTime;
	

	@Setter
	@Getter
	private Integer week;

	@Setter
	@Getter
	private Integer stat;

	
	@Setter
	@Getter
	private String userAccountName;
	
	@Getter
	@Setter
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date searchStartDate;
	
	@Getter
	@Setter
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date searchEndDate;
	
	@Getter
	@Setter
	private String address;
	
	@Setter
	@Getter
	private Integer orderStatus;
	
	@Setter
	@Getter
	private String statusName;
	
	
	@Getter
	@Setter
	private String startTime;
	
	@Getter
	@Setter
	private String endtime;
	
	@Getter
	@Setter
	private String randCode;
	
	
	@Getter
    @Setter
    private String validCode;
	
	
	//寻找订单不在状态为订单失效标志位
	@Getter
	@Setter
	private String isOrderFail;


}
