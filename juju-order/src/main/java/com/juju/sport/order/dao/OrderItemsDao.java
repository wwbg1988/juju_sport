package com.juju.sport.order.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.dto.ParameterDto;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.order.constants.OrderStatus;
import com.juju.sport.order.dto.OrderItemsDto;
import com.juju.sport.order.mapper.OrderItemsMapper;
import com.juju.sport.order.mapper.OwnOrderItemsMapper;
import com.juju.sport.order.pojo.OrderItems;
import com.juju.sport.order.pojo.OrderItemsExample;
import com.juju.sport.order.pojo.OrderItemsExample.Criteria;

@Repository
public class OrderItemsDao extends MyBatisBaseDao<OrderItems> {

	@Autowired
	@Getter
	private OrderItemsMapper mapper;
	
	@Autowired
	@Getter
	private OwnOrderItemsMapper ownOrderItemsMapper;
	
	
	public List<OrderItems> findOrderByUserAccountId(String userAccountId){
		OrderItemsExample example = new OrderItemsExample();
		OrderItemsExample.Criteria criteria = example.createCriteria();
		criteria.andUserAccountIdEqualTo(userAccountId);
		List<OrderItems> orderItemslist =  mapper.selectByExample(example);
		return orderItemslist;
	}
	
	
	public int findOrder(OrderItemsDto orderItemsDto){
		OrderItemsExample example = new OrderItemsExample();
		OrderItemsExample.Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(orderItemsDto.getOwnerAccountId())){
			criteria.andOwnerAccountIdEqualTo(orderItemsDto.getOwnerAccountId());
		}
		if(!StringUtils.isEmpty(orderItemsDto.getUserAccountId())){
			criteria.andUserAccountIdEqualTo(orderItemsDto.getUserAccountId());
		}	
		if(orderItemsDto.getSearchEndDate()!=null&&orderItemsDto.getSearchStartDate()!=null){
			criteria.andDateBetween(orderItemsDto.getSearchStartDate(), orderItemsDto.getSearchEndDate());
		}
		
		if(!StringUtils.isEmpty(orderItemsDto.getStartTime()) && !StringUtils.isEmpty(orderItemsDto.getStartTime())){
			criteria.andDateBetween(DateUtils.parse(orderItemsDto.getStartTime(), DateUtils.YMD_DASH), DateUtils.parse(orderItemsDto.getEndTime(),DateUtils.YMD_DASH));
		}		
		
		criteria.andStatEqualTo(orderItemsDto.getStat());
		return mapper.countByExample(example);
	}
	
	public int findCountsOrderItems(OrderItemsDto orderItemsDto){
		OrderItemsExample example = new OrderItemsExample();
		OrderItemsExample.Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(orderItemsDto.getOrderTime())){
			criteria.andOrderTimeEqualTo(orderItemsDto.getOrderTime());
		}
		if(!StringUtils.isEmpty(orderItemsDto.getEndTime())){
			criteria.andEndTimeEqualTo(orderItemsDto.getEndTime());
		}
		if(orderItemsDto.getDate()!=null){
			criteria.andDateEqualTo(DateUtils.parse(orderItemsDto.getDate(), DateUtils.YMD_DASH));
		}
		if(!StringUtils.isEmpty(orderItemsDto.getSpaceId())){
			criteria.andSpaceIdEqualTo(orderItemsDto.getSpaceId());
		}
		if(orderItemsDto.getOrderStatus()!=null){
			criteria.andOrderStatusEqualTo(orderItemsDto.getOrderStatus());
		}
		if(!StringUtils.isEmpty(orderItemsDto.getUserAccountId())){
			criteria.andUserAccountIdEqualTo(orderItemsDto.getUserAccountId());
		}
		if(!StringUtils.isEmpty(orderItemsDto.getIsOrderFail())){
			List<Integer> values = new ArrayList<Integer>();
			values.add(OrderStatus.SellerRefund.getValue());
			values.add(OrderStatus.OrderFailed.getValue());
			criteria.andOrderStatusNotIn(values);
		}
		return mapper.countByExample(example);
		
	}
	
	public int findCountOrderSpace(OrderItemsDto orderItemsDto){
		OrderItemsExample example = new OrderItemsExample();
		OrderItemsExample.Criteria criteria = example.createCriteria();
		criteria.andSpaceIdEqualTo(orderItemsDto.getSpaceId());
		criteria.andDateGreaterThanOrEqualTo(new Date());
		if(!StringUtils.isEmpty(orderItemsDto.getOrderTime())&&!StringUtils.isEmpty(orderItemsDto.getEndTime())&&!StringUtils.isEmpty(orderItemsDto.getStat()+"")&&orderItemsDto.getStat()==900){
			criteria.andOrderTimeBetween(orderItemsDto.getOrderTime(), orderItemsDto.getEndTime());
		}
		if(!StringUtils.isEmpty(orderItemsDto.getOrderTime())&&!StringUtils.isEmpty(orderItemsDto.getEndTime())&&!StringUtils.isEmpty(orderItemsDto.getStat()+"")&&orderItemsDto.getStat()==901){
			criteria.andEndTimeBetween(orderItemsDto.getOrderTime(), orderItemsDto.getEndTime());
		}
		if(orderItemsDto.getWeek()==null||orderItemsDto.getWeek()==0){
			
		}else{
			criteria.andWeekEqualTo(orderItemsDto.getWeek());
		}
		return mapper.countByExample(example);
	}

	public List<OrderItems> findBy(OrderItemsDto orderItemsDto, PageQuery page) {
		OrderItemsExample example = new OrderItemsExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(orderItemsDto.getOwnerAccountId())){
			criteria.andOwnerAccountIdEqualTo(orderItemsDto.getOwnerAccountId());
		}
		if(!StringUtils.isEmpty(orderItemsDto.getUserAccountId())){
			criteria.andUserAccountIdEqualTo(orderItemsDto.getUserAccountId());
		}	
		if(orderItemsDto.getSearchEndDate()!=null&&orderItemsDto.getSearchStartDate()!=null){
			criteria.andDateBetween(orderItemsDto.getSearchStartDate(), orderItemsDto.getSearchEndDate());
		}
		
		if(!StringUtils.isEmpty(orderItemsDto.getStartTime()) && !StringUtils.isEmpty(orderItemsDto.getStartTime())){
			criteria.andDateBetween(DateUtils.parse(orderItemsDto.getStartTime(), DateUtils.YMD_DASH), DateUtils.parse(orderItemsDto.getEndTime(),DateUtils.YMD_DASH));
		}
		
//		if(orderItemsDto.getSearchEndDate()!=null&&orderItemsDto.getSearchStartDate()!=null){
//			criteria.andDateBetween(orderItemsDto.getSearchStartDate(), orderItemsDto.getSearchEndDate());
//		}
		
		criteria.andStatEqualTo(orderItemsDto.getStat());
		example.setOrderByClause(" date desc, order_time DESC limit " + page.getStartNum() + ", " + page.getPageSize());
		return mapper.selectByExample(example);
	}

	public long count(OrderItemsDto orderItemsDto) {
		OrderItemsExample example = new OrderItemsExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}

	public Criteria pottingSql(Criteria criteria,OrderItemsDto orderItemsDto) {
		criteria.andStatEqualTo(DataStatus.ENABLED);
		// 对应的主订单的Id
		if (!StringUtils.isEmpty(orderItemsDto.getOrderId())) {
			criteria.andOrderIdEqualTo(orderItemsDto.getOrderId());
		}
		// 订单明细自身的订单编号
		if (!StringUtils.isEmpty(orderItemsDto.getOrderNo())) {
			criteria.andOrderNoEqualTo(orderItemsDto.getOrderNo());
		}
		// 购买者
		if (!StringUtils.isEmpty(orderItemsDto.getUserAccountId())) {
			criteria.andUserAccountIdEqualTo(orderItemsDto.getUserAccountId());
		}
		// 场馆用户Id 即 商家
		if (!StringUtils.isEmpty(orderItemsDto.getOwnerAccountId())) {
			criteria.andOwnerAccountIdEqualTo(orderItemsDto.getOwnerAccountId());
		}
		//预约时间
		if (!StringUtils.isEmpty(orderItemsDto.getDate())) {
			criteria.andDateEqualTo(DateUtils.parse(orderItemsDto.getDate(), DateUtils.YMD_DASH));
		}
		//订单 场地Id
		if (!StringUtils.isEmpty(orderItemsDto.getSpaceId())) {
			criteria.andSpaceIdEqualTo(orderItemsDto.getSpaceId());
		}
		//联系人手机号
        if (!StringUtils.isEmpty(orderItemsDto.getTelephone())) {
            criteria.andTelephoneEqualTo(orderItemsDto.getTelephone());
        }
        //取票密码
        if (!StringUtils.isEmpty(orderItemsDto.getValidCode())) {
            criteria.andValidCodeEqualTo(orderItemsDto.getValidCode());
        }
		
		return criteria;
	}

	public List<OrderItems> findListBy(OrderItemsDto orderItemsDto) {
		OrderItemsExample example = new OrderItemsExample();
		Criteria criteria = example.createCriteria();
		
		criteria = pottingSql(criteria,orderItemsDto);
		
		
		return mapper.selectByExample(example);
	}

	/**
	 * 
		 * 此方法描述的是：根据子订单Id 查询  自订单信息
		 * @author: cwftalus@163.com
		 * @version: 2015年5月5日 下午5:16:04
	 */
	public OrderItems findByItemId(String orderItemId) {
		return mapper.selectByPrimaryKey(orderItemId);
	}
	/**
     * 
         * 此方法描述的是：根据子订单Id 查询  自订单信息
         * @author: cwftalus@163.com
         * @version: 2015年5月5日 下午5:16:04
     */
    public List<OrderItems> findByOrderNo(String orderNO) {
        OrderItemsExample example = new OrderItemsExample();
        OrderItemsExample.Criteria criteria = example.createCriteria();
        criteria.andOrderNoEqualTo(orderNO);
        return mapper.selectByExample(example);
    }
	
	

	/**
	 * 
		 * 此方法描述的是：已经预订则不能再进行预订功能
		 * @author: cwftalus@163.com
		 * @version: 2015年5月8日 上午9:12:11
	 */
	public List<OrderItems> selectCheckBuy(List<ParameterDto> paramterList) {		
		List<OrderItems> resultList = ownOrderItemsMapper.selectCheckBuy(paramterList);
		return resultList;
	}

	public List<OrderItems> selectCheckSpaceBuy(List<ParameterDto> paramterList) {		
		List<OrderItems> resultList = ownOrderItemsMapper.selectCheckBuy(paramterList);
		return resultList;
	}
	
	/**
	 * 
		 * 此方法描述的是：完成支付返回接口 针对 订单号进行后续处理
		 * @author: cwftalus@163.com
		 * @version: 2015年5月12日 下午3:28:56
	 */	
	public void updateOrderItemByOrderId(String orderId,int orderStatus,int paymentStatus) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderId",orderId);
		paramMap.put("orderStatus",orderStatus);
		paramMap.put("paymentStatus",paymentStatus);
		paramMap.put("lastUpdateTime",new Date());		
		ownOrderItemsMapper.updateOrderItemByOrderId(paramMap);
	}

	public void updateOrderItemByOrderNo(String orderNo,int orderStatus,int paymentStatus) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderNo",orderNo);
		paramMap.put("orderStatus",orderStatus);
		paramMap.put("paymentStatus",paymentStatus);
		paramMap.put("lastUpdateTime",new Date());		
		ownOrderItemsMapper.updateOrderItemByOrderNo(paramMap);
	}

	public List<OrderItemsDto> findListByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		return ownOrderItemsMapper.findListByOrderNo(orderNo);
	}
	
	public List<OrderItemsDto> findListByOrderId(String orderId) {
		// TODO Auto-generated method stub
		return ownOrderItemsMapper.findListByOrderId(orderId);
	}
}
