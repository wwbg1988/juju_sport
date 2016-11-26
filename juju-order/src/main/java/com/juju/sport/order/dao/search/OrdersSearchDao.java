package com.juju.sport.order.dao.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Repository;

import com.juju.sport.order.pojo.search.OrdersSearch;
import com.juju.sport.order.repository.OrdersRepository;

@Repository
public class OrdersSearchDao {

	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private ElasticsearchTemplate template;
	
	public void save(OrdersSearch shop){
		ordersRepository.save(shop);
	}
	
	public void remove(String id) {
		ordersRepository.delete(id);
	}
}
