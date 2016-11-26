package com.juju.sport.order.service;

import org.springframework.stereotype.Service;

import com.juju.sport.order.dto.OrderUserDto;

@Service
public interface IOrderMappService {
	public void insertOrder(OrderUserDto orderUserDto);
	
	public int findCountOrder(OrderUserDto orderUserDto);
}
