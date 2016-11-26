package com.juju.sport.sync.handler.orders;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.order.dto.OrderDto;
import com.juju.sport.order.service.IOrderSearchService;
import com.juju.sport.sync.annotation.TableName;
import com.juju.sport.sync.handler.IDataHandler;
import com.juju.sport.sync.model.DataInfo;

@TableName("juju_orders")
@Service
public class OrdersHandler implements IDataHandler {

	protected static final Log logger = LogFactory.getLog(OrdersHandler.class);
	
	@Autowired
	private IOrderSearchService orderSearchService;
	
	@Override
	public void create(DataInfo dataInfo) {
		logger.info("create" + dataInfo);
		OrderDto orderDto = populateOrderDto(dataInfo);		
		orderSearchService.saveOrUpdate(orderDto);
	}

	@Override
	public void update(DataInfo dataInfo) {
		logger.info("update" + dataInfo);
		OrderDto orderDto = populateOrderDto(dataInfo);		
		orderSearchService.saveOrUpdate(orderDto);
	}

	@Override
	public void delete(DataInfo dataInfo) {
		logger.info("delete" + dataInfo);
		orderSearchService.remove(dataInfo.getColumnStringValue("id"));
	}

	private OrderDto populateOrderDto(DataInfo dataInfo) {
		OrderDto orderDto = new OrderDto();
		orderDto.setId(dataInfo.getColumnStringValue("id"));
		orderDto.setOrderNo(dataInfo.getColumnStringValue("order_no"));
		orderDto.setTelephone(dataInfo.getColumnStringValue("telephone"));;  
		orderDto.setOrderTotal(dataInfo.getColumnIntegerValue("order_total"));
		orderDto.setOrderStatus(dataInfo.getColumnIntegerValue("order_status"));  
		orderDto.setPaymentStatus(dataInfo.getColumnIntegerValue("payment_status"));
		orderDto.setUserAccountId(dataInfo.getColumnStringValue("user_account_id"));
		orderDto.setOwnerAccountId(dataInfo.getColumnStringValue("owner_account_id"));  
		orderDto.setOrderTime(dataInfo.getColumnDateValue("order_time"));
		orderDto.setCreateTime(dataInfo.getColumnDateValue("create_time"));  
		orderDto.setLastUpdateTime(dataInfo.getColumnDateValue("last_update_time"));
		orderDto.setStat(dataInfo.getColumnIntegerValue("stat"));
		return orderDto;
	}

}
