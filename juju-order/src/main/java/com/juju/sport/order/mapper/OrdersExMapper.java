package com.juju.sport.order.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.order.dto.OrderDto;
import com.juju.sport.order.dto.OrderQuery;

public interface OrdersExMapper {

	List<OrderDto> findByPage(@Param("query")OrderQuery query, @Param("page")PageQuery page);
	
	Long count(@Param("query")OrderQuery query);
}
