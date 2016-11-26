package com.juju.sport.order.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class OrderBuyDto implements Serializable{
	@Getter
	@Setter
	private String spaceId;

	@Getter
	@Setter
	private String userAccountId;
	
	@Getter
	@Setter
	private String ownerAccountId;
	
	@Getter
	@Setter
	private String telephone;
	
	@Getter
	@Setter
	private String orderDate;
	
	@Getter
	@Setter
	private String orderTime;
	
	@Getter
	@Setter
	private Integer orderTotal;
	
	@Getter
	@Setter
	private String orderItems;
	
	@Getter
	@Setter
	private String itemPrices;
	
	@Getter
	@Setter
	private String itemSpaceName;
	
	@Getter
	@Setter
	private String orderNo;
	
	@Getter
	@Setter
	private Integer orderStatus;
	
	
	@Getter
	@Setter
	private String sportType;
	
	@Getter
	@Setter
	private String validCode;
	
	@Getter
	@Setter
	private List<BuyOrderItemsDto> orders;

	
	//:orderItems
}
