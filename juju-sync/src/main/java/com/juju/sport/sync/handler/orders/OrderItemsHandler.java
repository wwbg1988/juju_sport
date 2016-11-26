package com.juju.sport.sync.handler.orders;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.order.dto.OrderItemsDto;
import com.juju.sport.order.service.IOrderItemsSearchService;
import com.juju.sport.sync.annotation.TableName;
import com.juju.sport.sync.handler.IDataHandler;
import com.juju.sport.sync.model.DataInfo;

@TableName("juju_orders_items")
@Service
public class OrderItemsHandler implements IDataHandler {

	protected static final Log logger = LogFactory.getLog(OrderItemsHandler.class);
	
	@Autowired
	public IOrderItemsSearchService orderSearchService;
	
	@Override
	public void create(DataInfo dataInfo) {
		logger.info("create" + dataInfo);	
		OrderItemsDto search = populateOrderItems(dataInfo);		  
		orderSearchService.saveOrUpdate(search);
	}

	@Override
	public void update(DataInfo dataInfo) {
		logger.info("update" + dataInfo);
		OrderItemsDto search = populateOrderItems(dataInfo);	
		orderSearchService.saveOrUpdate(search);
	}

	@Override
	public void delete(DataInfo dataInfo) {
		logger.info("delete" + dataInfo);
		orderSearchService.remove(dataInfo.getColumnStringValue("id"));
	}


	private OrderItemsDto populateOrderItems(DataInfo dataInfo) {
		OrderItemsDto search = new OrderItemsDto();
		search.setId(dataInfo.getColumnStringValue("id"));
		search.setOrderId(dataInfo.getColumnStringValue("order_id"));
		search.setOrderNo(dataInfo.getColumnStringValue("order_no"));
		search.setDate(dataInfo.getColumnStringValue("date"));
		search.setOrderTime(dataInfo.getColumnStringValue("order_time"));
		search.setEndTime(dataInfo.getColumnStringValue("end_time"));
		search.setSpaceId(dataInfo.getColumnStringValue("space_id"));;
		search.setSpaceName(dataInfo.getColumnStringValue("space_name"));
		search.setUserAccountId(dataInfo.getColumnStringValue("user_account_id"));
		search.setOwnerAccountId(dataInfo.getColumnStringValue("owner_account_id"));
		search.setTelephone(dataInfo.getColumnStringValue("telephone"));
		search.setOrderTotal(dataInfo.getColumnIntegerValue("order_total"));
		search.setCreateTime(dataInfo.getColumnDateValue("create_time"));
		search.setLastUpdateTime(dataInfo.getColumnDateValue("last_update_time"));
		search.setStat(dataInfo.getColumnIntegerValue("stat"));
		return search;
	}

}
