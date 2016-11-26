package com.juju.sport.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.order.dao.search.OrdersSearchDao;
import com.juju.sport.order.dto.OrderDto;
import com.juju.sport.order.pojo.search.OrdersSearch;
import com.juju.sport.order.service.IOrderSearchService;

@Service
public class OrderSearchServiceImpl implements IOrderSearchService {

	@Autowired
	private OrdersSearchDao orderSearchDao;
	
	@Override
	public void saveOrUpdate(OrderDto order) {
		OrdersSearch ordersSearch = BeanUtils.createBeanByTarget(order, OrdersSearch.class);
		orderSearchDao.save(ordersSearch);
	}

	@Override
	public void remove(String id) {
		orderSearchDao.remove(id);
	}

}
