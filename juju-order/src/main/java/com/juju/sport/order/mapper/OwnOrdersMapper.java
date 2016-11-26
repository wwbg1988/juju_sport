package com.juju.sport.order.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Param;


public interface OwnOrdersMapper {
	void updateOrdersByOrderNo(HashMap<String, Object> paramMap);
}
