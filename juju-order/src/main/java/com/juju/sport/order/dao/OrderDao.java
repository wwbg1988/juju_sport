package com.juju.sport.order.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.order.dto.OrderDto;
import com.juju.sport.order.dto.OrderQuery;
import com.juju.sport.order.mapper.OrdersExMapper;
import com.juju.sport.order.mapper.OrdersMapper;
import com.juju.sport.order.mapper.OwnOrdersMapper;
import com.juju.sport.order.pojo.Orders;
import com.juju.sport.order.pojo.OrdersExample;
import com.juju.sport.order.pojo.OrdersExample.Criteria;


//2015年3月23日 下午4:56:29
@Repository
public class OrderDao extends MyBatisBaseDao<Orders>{

	@Autowired
	@Getter
	private OrdersMapper mapper;
	
	@Autowired
	@Getter
	private OwnOrdersMapper ownMappers;
	
	@Autowired
	@Getter
	private OrdersExMapper orderExMapper;
	
	//findOne
	public Orders findOne(String id){
		OrdersExample example = new OrdersExample();
		OrdersExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause("id");
		return mapper.selectByPrimaryKey(id);
	}
		
	//findAll
	public List<Orders> findAll(){
		OrdersExample example = new OrdersExample();
		OrdersExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause("id");
		return mapper.selectByExample(example);
	}
	
	//find
	public List<Orders> find(OrderQuery query , PageQuery page){
		OrdersExample example = new OrdersExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		
		if(query.getBeginTime()!=null&&query.getEndTime()!=null){//创建时间在 某  某  之间
			criteria.andOrderTimeBetween(query.getBeginTime(), query.getEndTime());
			//criteria.andOrderTimeLessThan(query.getBeginTime());   //表示小于 等于
			//criteria.andOrderTimeGreaterThan(query.getEndTime());  //表示大于 等于
		}
		
		if(StringUtils.isNotEmpty(query.getOrderNo())){//订单号
			criteria.andOrderNoLike(query.getOrderNo() + "%");
		}
		

		if(query.getOwnerAccountId()!=null&&query.getOwnerAccountId().trim().length()>0){ //订单人 ownerId
			criteria.andOwnerAccountIdLike(query.getOwnerAccountId() + "%");
		}
		
		if(query.getBeginAmount()!=null&&query.getEndAmount()!=null){  //订单金额
			criteria.andOrderTotalBetween(query.getBeginAmount(), query.getEndAmount());
		}
		
		if(query.getOrderStatus()!=null){  //订单状态
			criteria.andOrderStatusEqualTo(query.getOrderStatus());
		}
		
		if(query.getPaymentStatus()!=null){  //支付状态
			criteria.andPaymentStatusEqualTo(query.getPaymentStatus());
		}
		
		//if(query.getStat()!=null){  //订单stat
			//criteria.andStatEqualTo(query.getStat());
			
		//}
		
		example.setOrderByClause("create_time asc limit " + page.getStartNum() + ", " + page.getPageSize());
		List<Orders> list = mapper.selectByExample(example);
		return list;
	}
	
	public List<OrderDto> findByPage(OrderQuery query , PageQuery page) {
		if(StringUtils.isEmpty(query.getOrderNo())) {
			query.setOrderNo(null);
		} else {
			query.setOrderNo("%" + query.getOrderNo() + "%");
		}
		return orderExMapper.findByPage(query, page);
	}
	
	//count 查询总记录数
	public long count(OrderQuery query){
		OrdersExample example = new OrdersExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);

		if(query.getBeginTime()!=null&&query.getEndTime()!=null){//创建时间在 某  某  之间
			//criteria.andOrderTimeBetween(query.getBeginTime(), query.getEndTime());
			criteria.andOrderTimeGreaterThanOrEqualTo(query.getBeginTime());   //表示小于 等于
			criteria.andOrderTimeLessThan(query.getEndTime());  //表示大于 等于
		}
		
		if(StringUtils.isNotEmpty(query.getOrderNo())){//订单号
			criteria.andOrderNoLike(query.getOrderNo());
		}
		
		
		if(StringUtils.isNotEmpty(query.getOwnerAccountId())){  //预约场地 ownerAccountId
			criteria.andOwnerAccountIdLike(query.getOwnerAccountId() + "%");
		}
		
		if(query.getBeginAmount()!=null&&query.getEndAmount()!=null){  //订单金额
			criteria.andOrderTotalBetween(query.getBeginAmount(), query.getEndAmount());
		}
		
		if(query.getOrderStatus()!=null){  //订单状态
			criteria.andOrderStatusEqualTo(query.getOrderStatus());
		}
		
		if(query.getPaymentStatus()!=null){  //支付状态
			criteria.andPaymentStatusEqualTo(query.getPaymentStatus());
		}
		
		/*if(query.getStat()!=null){  //订单stat
			criteria.andStatEqualTo(query.getStat());
		}*/
		
		return mapper.countByExample(example);
	}
	


	/**
	 * 
		 * 此方法描述的是：根据订单Id 查询  自订单信息
		 * @author: cwftalus@163.com
		 * @version: 2015年5月5日 下午5:16:04
	 */
	public Orders findByOrderId(String orderId,String orderNo) {
		OrdersExample example = new OrdersExample();
		OrdersExample.Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(orderId)){
			criteria.andIdEqualTo(orderId);	
		}
		
		if(!StringUtils.isEmpty(orderNo)){
			criteria.andOrderNoEqualTo(orderNo);	
		}
		
		criteria.andStatEqualTo(DataStatus.ENABLED);

		List<Orders> resultList =  mapper.selectByExample(example);
		if(resultList!=null && resultList.size()>0){
			return resultList.get(0);
		}else{
			return null;
		}
//		
//		OrdersExample example = new OrdersExample();
//		return mapper.selectByPrimaryKey(orderId);
	}

	/**
	 * 
		 * 此方法描述的是：完成支付返回接口 针对 订单号进行后续处理
		 * @author: cwftalus@163.com
		 * @version: 2015年5月12日 下午3:28:56
	 */	
	public void updateOrdersByOrderNo(String orderNo,int orderStatus,int paymentStatus) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderNo",orderNo);
		paramMap.put("orderStatus",orderStatus);
		paramMap.put("paymentStatus",paymentStatus);
		paramMap.put("lastUpdateTime",new Date());
		ownMappers.updateOrdersByOrderNo(paramMap);
		
	}
	
	
}
