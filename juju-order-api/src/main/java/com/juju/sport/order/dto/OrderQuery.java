package com.juju.sport.order.dto;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


//2015年3月26日 下午12:49:07  by bake

/**
 * 用来封装需要传递的参数
 */
@ToString
public class OrderQuery implements Serializable{
	
	@Getter
	@Setter
	private Date beginTime;   //开始时间
	
	@Getter
	@Setter
	private Date endTime;
	
	@Getter
	@Setter
	private String orderNo;
	
	@Getter
	@Setter
	private String userAccountId;
	
	@Getter
	@Setter
	private String ownerAccountId;
	
	@Getter
	@Setter
	private Integer beginAmount;    //金额区间   开始金额
	
	@Getter
	@Setter
	private Integer endAmount;
	
	@Getter
	@Setter
	private Integer orderStatus;      //订单状态
	
	@Getter
	@Setter
	private Integer paymentStatus;    //支付状态
	
	@Getter
	@Setter
	private String userAcountIdName;  //预约用户
	
	@Getter
	@Setter
	private String ownerAccountIdName;  //预约场馆、场地
	
	
	/*@Getter
	@Setter
	private Integer stat;*/     //订单stat有效或者无效

}
