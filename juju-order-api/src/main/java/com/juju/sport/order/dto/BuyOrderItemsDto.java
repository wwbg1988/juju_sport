package com.juju.sport.order.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class BuyOrderItemsDto implements Serializable{
	@Getter
	@Setter
	private String spaceId;
	
	@Getter
	@Setter
	private String orderTime;
	
	@Getter
	@Setter
	private String spaceName;
}
