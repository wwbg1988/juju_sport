package com.juju.sport.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.order.dao.OrderMappDao;
import com.juju.sport.order.dto.OrderUserDto;
import com.juju.sport.order.service.IOrderMappService;

@Repository
public class OrderMappServiceImpl implements IOrderMappService{
	
	@Autowired
	private OrderMappDao orderMappDao;

	@Override
	public void insertOrder(OrderUserDto orderUserDto) {
		orderMappDao.insert(orderUserDto);
	}

	@Override
	public int findCountOrder(OrderUserDto orderUserDto) {
		return orderMappDao.findCount(orderUserDto);
	}

}
