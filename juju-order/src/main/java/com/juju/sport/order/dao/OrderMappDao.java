package com.juju.sport.order.dao;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.order.dto.OrderUserDto;
import com.juju.sport.order.mapper.UserOrderMapper;
import com.juju.sport.order.pojo.UserOrder;
import com.juju.sport.order.pojo.UserOrderExample;
import com.juju.sport.order.pojo.UserOrderExample.Criteria;

@Repository
public class OrderMappDao {

	@Autowired
	@Getter
	private UserOrderMapper mapper;
	
	public void insert(OrderUserDto orderUserDto){
		UserOrder userOrder =  new UserOrder();
		BeanUtils.copyProperties(orderUserDto, userOrder);
		mapper.insert(userOrder);
	}
	
	public int findCount(OrderUserDto orderUserDto){
		UserOrder userOrder = new UserOrder();
		BeanUtils.copyProperties(orderUserDto, userOrder);
		UserOrderExample example = new UserOrderExample();
		Criteria criteria = example.createCriteria();
		criteria.andOrderIdEqualTo(userOrder.getOrderId());
		if(!StringUtils.isEmpty(orderUserDto.getUserAccountId())){
			criteria.andUserAccountIdEqualTo(orderUserDto.getUserAccountId());
		}
		return mapper.countByExample(example);
	}
	
	
}
