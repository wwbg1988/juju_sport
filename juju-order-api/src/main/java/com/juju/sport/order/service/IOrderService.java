package com.juju.sport.order.service;


import java.util.Map;

import org.springframework.stereotype.Service;

import com.juju.sport.common.dto.Pair;
import com.juju.sport.common.model.ListResult;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.order.dto.OrderBuyDto;
import com.juju.sport.order.dto.OrderDto;
import com.juju.sport.order.dto.OrderQuery;

//2015年3月23日 下午4:52:40  by bake
@Service
public interface IOrderService {

	// findOne
	OrderDto findOne(String id);

	// findAll
	ListResult<OrderDto> findAll();

	// find
	PageResult<OrderDto> find(OrderQuery query, PageQuery page);

	// update
	RequestResult updateOrder(OrderDto orderDto);

	// delete
	RequestResult deleteOrder(String id);

	// getType
	ListResult<Pair<Integer, String>> getOrderType();

	ListResult<Pair<Integer, String>> getPaymentType();

	ListResult<Pair<Integer, String>> getOrder();

	/**
	 * 
	 * 此方法描述的是：保存订单信息
	 * 
	 * @author: cwftalus@163.com
	 * @version: 2015年3月27日 上午11:50:18
	 */
	public String saveOrUpdate(OrderBuyDto orderBuyDto);

	
	/**
	 * 
		 * 此方法描述的是：根据订单Id 查询  自订单信息
		 * @author: cwftalus@163.com
		 * @version: 2015年5月5日 下午5:16:04
	 */
	OrderDto findByOrderId(String orderId,String orderNo);
	
	
	
	/**
	 * 
		 * 此方法描述的是：完成支付返回接口 针对 订单号进行后续处理
		 * @author: cwftalus@163.com
		 * @version: 2015年5月12日 下午3:28:56
	 */
	void updateOrdersByOrderNo(String orderNo,int orderStatus,int paymentStatus);

    
    /**     
     * saveTOrUpdate：刷卡生成事务订单
     * @param orderBuyDto
     * @return String
     * @author Vincent
     * @date 2015年5月27日 下午3:53:09	 
     */
    String saveTOrUpdate(OrderBuyDto orderBuyDto,Map<String,String> map);

}
