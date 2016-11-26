package com.juju.sport.order.dao.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Repository;
import com.juju.sport.order.pojo.search.OrderItemsSearch;
import com.juju.sport.order.repository.OrderItemsRepository;

@Repository
public class OrderItemsSearchDao {

	@Autowired
	private OrderItemsRepository orderItemsRepository;
	
	@Autowired
	private ElasticsearchTemplate template;
	
	public void saveOrUpdate(OrderItemsSearch orderItems){
		orderItemsRepository.save(orderItems);
	}
	
	public void remove(String id) {
		orderItemsRepository.delete(id);
	}
	
}
