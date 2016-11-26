package com.juju.sport.order.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.juju.sport.order.pojo.search.OrderItemsSearch;

public interface OrderItemsRepository extends ElasticsearchRepository<OrderItemsSearch, String> {
	
	
}
