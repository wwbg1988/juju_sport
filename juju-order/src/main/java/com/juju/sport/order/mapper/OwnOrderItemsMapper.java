package com.juju.sport.order.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.juju.sport.common.dto.ParameterDto;
import com.juju.sport.order.dto.OrderItemsDto;
import com.juju.sport.order.pojo.OrderItems;

public interface OwnOrderItemsMapper {
	/**
	 * 包含场地信息
	 * DATE
	 * order_time
	 * space_id
	 * order_status
	 * **/
	List<OrderItems> selectCheckBuy(List<ParameterDto> list);

	/**
	 * 包含场地信息
	 * DATE
	 * order_time
	 * order_status
	 * **/
	List<OrderItems> selectCheckSpaceBuy(List<ParameterDto> list);
	
	void updateOrderItemByOrderId(HashMap<String, Object> paramMap);
	
	void updateOrderItemByOrderNo(HashMap<String, Object> paramMap);

	List<OrderItemsDto> findListByOrderNo(@Param("orderNo")String orderNo);
	
	List<OrderItemsDto> findListByOrderId(@Param("orderId")String orderId);
}
