package com.juju.sport.order.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class BuyOrderDto implements Serializable{
//	{
//	    "userid": "1212121212",
//	    "venueid": "12121212",
//	    "orderDate": "2015-05-12",
//	    "orderTel": "13040691917",
//	    "valiCode": "304033",
//	    "orders": [
//	        {
//	            "sessionid": "1",
//	            "orderTime": "17:00-18:00",
//	            "sessionName": "2号球场"
//	        },
//	        {
//	            "sessionid": "2",
//	            "orderTime": "18:00-19:00",
//	            "sessionName": "3号球场"
//	        }
//	    ]
//	}
	@Getter
	@Setter
	private String userAccountId;
	
	@Getter
	@Setter
	private String ownerAccountId;
	
	@Getter
	@Setter
	private String orderDate;
	
	@Getter
	@Setter
	private String orderTel;
	
	@Getter
	@Setter
	private String validCode;
	
	@Getter
	@Setter
	private List<BuyOrderItemsDto> orders;
	
}
