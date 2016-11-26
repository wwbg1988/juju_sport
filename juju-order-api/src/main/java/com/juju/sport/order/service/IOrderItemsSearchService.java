package com.juju.sport.order.service;

import com.juju.sport.order.dto.OrderItemsDto;

public interface IOrderItemsSearchService {

	public void saveOrUpdate(OrderItemsDto orderItems);
	
	public void remove(String id);
	
}
