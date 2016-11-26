package com.juju.sport.order.service.impl;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.DateRegExp;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.order.dao.search.OrderItemsSearchDao;
import com.juju.sport.order.dto.OrderItemsDto;
import com.juju.sport.order.pojo.search.OrderItemsSearch;
import com.juju.sport.order.service.IOrderItemsSearchService;

@Service
public class OrderItemsSearchServiceImpl implements IOrderItemsSearchService {
	
	@Autowired
	private OrderItemsSearchDao orderItemsSearchDao;

	@Override
	public void saveOrUpdate(OrderItemsDto orderItems) {
		OrderItemsSearch orderItemsSearch = BeanUtils.createBeanByTarget(orderItems, OrderItemsSearch.class, "date");
		if(StringUtils.isNotEmpty(orderItems.getDate())) {
			try {
				orderItemsSearch.setDate(DateRegExp.yyyy_MM_dd.parse(orderItems.getDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		orderItemsSearchDao.saveOrUpdate(orderItemsSearch);
	}

	@Override
	public void remove(String id) {
		orderItemsSearchDao.remove(id);
	}

}
