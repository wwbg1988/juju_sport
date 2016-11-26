package com.juju.sport.order.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.juju.sport.order.pojo.search.OrdersSearch;

public interface OrdersRepository extends ElasticsearchRepository<OrdersSearch, String>{

}
