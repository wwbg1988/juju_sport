package com.juju.sport.order.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.dto.Pair;
import com.juju.sport.common.model.ListResult;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.common.util.UUIDGenerator;
import com.juju.sport.order.constants.OrderStatus;
import com.juju.sport.order.constants.PaymentStatus;
import com.juju.sport.order.constants.Status;
import com.juju.sport.order.dao.OrderDao;
import com.juju.sport.order.dto.OrderBuyDto;
import com.juju.sport.order.dto.OrderDto;
import com.juju.sport.order.dto.OrderItemsDto;
import com.juju.sport.order.dto.OrderQuery;
import com.juju.sport.order.pojo.Orders;
import com.juju.sport.order.service.IOrderItemsService;
import com.juju.sport.order.service.IOrderService;


//2015年3月23日 下午4:54:22  by bake
@Service
public class OrderServiceImpl implements IOrderService{

    @Autowired
    private IOrderItemsService orderItemsService;
    
	@Autowired
	private OrderDao orderDao;
	
	//findOne.do
	@Override
	public OrderDto findOne(String id){
		Orders order = orderDao.findOne(id);
		return BeanUtils.createBeanByTarget(order, OrderDto.class);
	}
	
	//查询所有值
	@Override
	public ListResult<OrderDto> findAll() {
		List<Orders> results = orderDao.findAll();
		if(CollectionUtils.isEmpty(results)){
			return new ListResult<OrderDto>(new ArrayList<OrderDto>());
		}
		List<OrderDto> dtoList = BeanUtils.createBeanListByTarget(orderDao.findAll(), OrderDto.class);
		return new ListResult<OrderDto>(dtoList) ;
	}

	//分页查询所有值
	@Override
	public PageResult<OrderDto> find(OrderQuery query , PageQuery page) {
		List<OrderDto> orders = orderDao.findByPage(query , page);
		//List<OrderDto> orders = BeanUtils.createBeanListByTarget(results, OrderDto.class);
		for(OrderDto order : orders) {
			order.setOrderStatusName(OrderStatus.getStatusDesc(order.getOrderStatus()));
			
			order.setPaymentStatusName(PaymentStatus.getStatusDesc(order.getPaymentStatus()));
			
			order.setStatName(Status.getStatusDesc(order.getStat()));;
		}
		long total = orderDao.count(query);
		page.setTotal(total);
		return new PageResult<OrderDto>(page, orders);
	}
	
	
	//deleteOrder
	@Override
	public RequestResult deleteOrder(String id){
		Orders orders = orderDao.findOne(id);
		orders.setStat(0);
		orderDao.updateByPrimaryKey(orders);  //修改
		return new RequestResult(true , "删除成功！");
	}

	//获取订单状态的枚举值
	@Override
	public ListResult<Pair<Integer, String>> getOrderType(){
		//订单状态 0:等待买家付款,1:买家已付款,2:交易完成,-1:买家关闭,-2:卖家关闭,-3:卖家退款
		List<Pair<Integer, String>> reuslts = new ArrayList<Pair<Integer,String>>();
		for(OrderStatus status : OrderStatus.values()){
			reuslts.add(new Pair<Integer, String>(status.getValue(), status.getDescription()));
		}		
		return new ListResult<Pair<Integer,String>>(reuslts);
	}

	//获取订单支付类型的枚举值
	@Override
	public ListResult<Pair<Integer, String>> getPaymentType() {
		//支付状态 0 未支付  1 已支付
		List<Pair<Integer, String>> reuslts = new ArrayList<Pair<Integer,String>>();
		for (PaymentStatus status : PaymentStatus.values()) {
			reuslts.add(new Pair<Integer, String>(status.getValue(),status.getDescription()));
		}
		return new ListResult<Pair<Integer,String>>(reuslts);
	}
	
	//或者订单类型
	@Override
	public ListResult<Pair<Integer, String>> getOrder(){
		//0表示无效，1表示有效
		List<Pair<Integer, String>> reuslts = new ArrayList<Pair<Integer,String>>();
		for (Status status : Status.values()) {
			reuslts.add(new Pair<Integer, String>(status.getValue(),status.getDescription()));
		}
		return new ListResult<Pair<Integer,String>>(reuslts);
	}

	@Override
    public String saveOrUpdate(OrderBuyDto orderBuyDto) {
        Orders orders = BeanUtils.createBeanByTarget(orderBuyDto, Orders.class);
        String orderId = UUIDGenerator.getUUID();
        orders.setId(orderId);//订单Id
        orders.setOrderTime(DateUtils.parse(orderBuyDto.getOrderDate(), DateUtils.YMD_DASH));//预约时间
        orders.setOrderNo(orderBuyDto.getOrderNo());//订单编码
        orderDao.insert(orders);
        return orderId;
    }
	
	@Override
    @Transactional      
    public String saveTOrUpdate(OrderBuyDto orderBuyDto,Map<String,String> map) {
	 
        Orders orders = BeanUtils.createBeanByTarget(orderBuyDto, Orders.class);
        String orderId = UUIDGenerator.getUUID();
        orders.setId(orderId);//订单Id
        orders.setOrderTime(DateUtils.parse(orderBuyDto.getOrderDate(), DateUtils.YMD_DASH));//预约时间
        orders.setOrderNo(orderBuyDto.getOrderNo());//订单编码
        orders.setOrderStatus(OrderStatus.Finished.getValue());
        orders.setLastUpdateTime(new Date());
        //生成订单
        orderDao.insert(orders);
        
        OrderItemsDto orderItemsDto = new OrderItemsDto();
        orderItemsDto.setUsrType(DataStatus.CARD_USR);
        orderItemsDto.setTelephone(map.get("telephone"));
        orderItemsDto.setOrderStatus(OrderStatus.Finished.getValue());
        orderItemsDto.setOrderNo(DataStatus._ITEMS_ + System.currentTimeMillis());
        orderItemsDto.setDate(map.get("orderTime"));
        orderItemsDto.setUserAccountId(map.get("accountid"));
        //PWD表查询场馆ID
        orderItemsDto.setOwnerAccountId(map.get("ownerAccountId"));
        orderItemsDto.setOrderId(orderId);
        
        
        //保存详情订单
        orderItemsService.saveOrUpdate(orderItemsDto);
        
        return orderId;
    }

	@Override
	public RequestResult updateOrder(OrderDto orderDto) {
		//Orders orders = orderDao.findOne(orderDto.getId());
		System.out.println(orderDto.getUserAccountId()+"This is a  userAccountId  预约用户");
		System.out.println(orderDto.getTelephone());
		Orders orders = BeanUtils.createBeanByTarget(orderDto, Orders.class);
		//orders.setCreateTime();
		orderDao.updateByPrimaryKeySelective(orders);
		return new RequestResult(true , "修改成功！");
	}
	
	/**
	 * 
		 * 此方法描述的是：根据订单Id 查询  自订单信息
		 * @author: cwftalus@163.com
		 * @version: 2015年5月5日 下午5:16:04
	 */
	@Override
	public OrderDto findByOrderId(String orderId,String orderNo) {
		Orders orders = orderDao.findByOrderId(orderId,orderNo);
		OrderDto ordersDto = BeanUtils.createBeanByTarget(orders, OrderDto.class);
		return ordersDto;
	}

	/**
	 * 
		 * 此方法描述的是：完成支付返回接口 针对 订单号进行后续处理
		 * @author: cwftalus@163.com
		 * @version: 2015年5月12日 下午3:28:56
	 */	
	@Override
	public void updateOrdersByOrderNo(String orderNo,int orderStatus,int paymentStatus) {
		// TODO Auto-generated method stub
		orderDao.updateOrdersByOrderNo(orderNo,orderStatus,paymentStatus);
	}
}
