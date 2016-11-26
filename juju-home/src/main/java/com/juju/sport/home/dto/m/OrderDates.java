package com.juju.sport.home.dto.m;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class OrderDates implements Serializable{
	
	@Getter
	@Setter
	private String orderDate;
	
	@Getter
	@Setter
	private List<OrderTimes> orderTimes;
}
