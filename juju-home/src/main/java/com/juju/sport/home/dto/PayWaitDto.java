package com.juju.sport.home.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class PayWaitDto implements Serializable{
	@Getter
	@Setter
	private Integer orderTotal;
	
	@Getter
	@Setter
	private String orderNo;
	
	@Getter
	@Setter
	private String date;
}
