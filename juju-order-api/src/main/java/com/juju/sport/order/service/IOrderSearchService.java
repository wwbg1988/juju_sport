package com.juju.sport.order.service;

import com.juju.sport.order.dto.OrderDto;

public interface IOrderSearchService {

	public void saveOrUpdate(OrderDto order);
	
	public void remove(String id);
	
}
