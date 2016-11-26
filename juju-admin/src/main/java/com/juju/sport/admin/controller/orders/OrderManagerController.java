package com.juju.sport.admin.controller.orders;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.juju.sport.common.dto.Pair;
import com.juju.sport.common.ext.ExtPageQuery;
import com.juju.sport.common.model.ListResult;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.order.dto.OrderDto;
import com.juju.sport.order.dto.OrderQuery;
import com.juju.sport.order.service.IOrderService;

// 2015年3月23日 下午3:21:44 
@Controller
@RequestMapping(value="order" )
public class OrderManagerController {
	
	protected static final Log logger = LogFactory.getLog(OrderManagerController.class);
	
	@Autowired
	private IOrderService orderService;
	
	//订单管理
	@RequestMapping(value="index.do")
	public String index(){
        logger.debug("visit admin order manager page");
        return "/admin/order/order-manage";
    }
	
	//findAll.do
	@ResponseBody
	@RequestMapping(value="findAll.do")
	public ListResult<OrderDto> findAll(){
		return orderService.findAll();
	}
	
	//find.do
	@RequestMapping(value="find.do")
	@ResponseBody
	public PageResult<OrderDto> find(OrderQuery query , ExtPageQuery page){
 		return orderService.find(query , page.changeToPageQuery());
	}
	
	@RequestMapping(value="findOne.do")
	@ResponseBody
	public OrderDto findOne(String id){
		return orderService.findOne(id);
	}
	
	//updateOrder.do
	@ResponseBody
	@RequestMapping(value="updateOrder.do")
	public RequestResult updateOrder(OrderDto orderDto){
		return orderService.updateOrder(orderDto);
	}
	
	//deleteOrder.do
	@ResponseBody
	@RequestMapping(value="deleteOrder.do")
	public RequestResult deleteOrder(String deleteId){
		System.out.println("This is a delete Id " + deleteId);
		return orderService.deleteOrder(deleteId);
	}
	
	@ResponseBody
	@RequestMapping(value="getOrderType.do")
	public ListResult<Pair<Integer, String>> getOrderType(){
		return orderService.getOrderType();		
	}
	
	@ResponseBody
	@RequestMapping(value="getPaymentType.do")
	public ListResult<Pair<Integer, String>> getPaymentType(){
		return orderService.getPaymentType();
	}
	
	
	@ResponseBody
	@RequestMapping(value="getOrder.do")
	public ListResult<Pair<Integer, String>> getOrder(){
		return orderService.getOrder();
	}
	
}
