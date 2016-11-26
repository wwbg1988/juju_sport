package com.juju.sport.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.dto.ParameterDto;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.common.util.UUIDGenerator;
import com.juju.sport.order.dao.OrderItemsDao;
import com.juju.sport.order.dto.OrderItemsDto;
import com.juju.sport.order.pojo.OrderItems;
import com.juju.sport.order.service.IOrderItemsService;

@Repository
public class OrderItemsServiceImpl implements IOrderItemsService
{

    @Autowired
    private OrderItemsDao orderItemsDao;

    /**
     * 按用户ID查询用户订单
     * @author: jam
     * @version: 2015年4月11日 下午14:16:16
     */
    public List<OrderItemsDto> findOrderByUserAccountId(String userAccountId)
    {
        List<OrderItems> orderItemsList = orderItemsDao.findOrderByUserAccountId(userAccountId);
        List<OrderItemsDto> orderItemsDtoList = new ArrayList<OrderItemsDto>();
        orderItemsDtoList = BeanUtils.createBeanListByTarget(orderItemsList, OrderItems.class);
        return orderItemsDtoList;
    }

    @Override
    public PageResult<OrderItemsDto> findBy(OrderItemsDto orderItemsDto, PageQuery page)
    {
        List<OrderItems> results = orderItemsDao.findBy(orderItemsDto, page);
        List<OrderItemsDto> orderItemss = new ArrayList<OrderItemsDto>();//.createBeanListByTarget(results, OrderItemsDto.class);
        for (OrderItems orderItems : results)
        {
            OrderItemsDto orderItemsObjDto = BeanUtils.createBeanByTarget(orderItems, OrderItemsDto.class);
            orderItemsObjDto.setDate(DateUtils.format(orderItems.getDate(), DateUtils.YMD_DASH));
            orderItemss.add(orderItemsObjDto);
        }
        List<OrderItemsDto> resultList = new ArrayList<OrderItemsDto>();
        if (results != null && results.size() > 0)
        {
            for (int i = 0; i < results.size(); i++)
            {
                OrderItems orderItems = results.get(i);
                OrderItemsDto orderItemsDto2 = new OrderItemsDto();
                BeanUtils.copyProperties(orderItems, orderItemsDto2);
                String tempDate = DateUtils.format(orderItems.getDate(), "YYYY-MM-dd");
                orderItemsDto2.setDate(tempDate);
                if (orderItemsDto2.getTelephone() != null && !"".equals(orderItemsDto2.getTelephone())
                    && orderItemsDto2.getTelephone().length() > 7)
                {
                    String temp = orderItemsDto2.getTelephone();
                    temp = temp.substring(0, 3) + "****" + temp.substring(7, temp.length());
                    orderItemsDto2.setTelephone(temp);
                }
                //				Users users = userDao.getAllUsers(orderItemsDto2.getOwnerAccountId());
                //				if(users!=null){
                //					orderItemsDto2.
                //				}
                resultList.add(orderItemsDto2);

            }
        }
        long total = orderItemsDao.findOrder(orderItemsDto);
        page.setTotal(total);
        return new PageResult<OrderItemsDto>(page, resultList);
    }

    @Override
    public int findCount(OrderItemsDto orderItemsDto)
    {
        return orderItemsDao.findCountOrderSpace(orderItemsDto);
    }

    public void saveOrUpdate(List<OrderItemsDto> orderItemsList)
    {
        for (OrderItemsDto orderItemDto : orderItemsList)
        {
            System.out.println("orderItemDto" + orderItemDto);
            OrderItems orderItem = BeanUtils.createBeanByTarget(orderItemDto, OrderItems.class);
            orderItem.setDate(DateUtils.parse(orderItemDto.getDate(), DateUtils.YMD_DASH));
            orderItemsDao.insert(orderItem);
        }
    }

    @Override
    public List<OrderItemsDto> findListBy(OrderItemsDto orderItemsDto)
    {
        List<OrderItems> results = orderItemsDao.findListBy(orderItemsDto);
        List<OrderItemsDto> OrderItemss = new ArrayList<OrderItemsDto>();
        if (results != null && results.size() > 0)
        {
            for (OrderItems orderItems : results)
            {
                String tempDate = DateUtils.format(orderItems.getDate(), DateUtils.YMD_DASH);
                OrderItemsDto tempDto = new OrderItemsDto();
                BeanUtils.copyProperties(orderItems, tempDto);
                tempDto.setDate(tempDate);
                OrderItemss.add(tempDto);
            }
        }
        //		List<OrderItemsDto> OrderItemss = BeanUtils.createBeanListByTarget(results, OrderItemsDto.class);
        return OrderItemss;
    }

    @Override
    public int findOrderCount(OrderItemsDto orderItemsDto)
    {
        return orderItemsDao.findOrder(orderItemsDto);
    }

    @Override
    public String saveOrUpdate(OrderItemsDto orderItemsDto)
    {
        OrderItems orderItem = BeanUtils.createBeanByTarget(orderItemsDto, OrderItems.class);
        String orderItemId = UUIDGenerator.getUUID();
        orderItem.setId(orderItemId);
        orderItem.setDate(DateUtils.parse(orderItemsDto.getDate(), DateUtils.YMD_DASH));
        orderItemsDao.insert(orderItem);
        return orderItemId;
    }

    /**
     * 
    	 * 此方法描述的是：根据子订单Id 查询  自订单信息
    	 * @author: cwftalus@163.com
    	 * @version: 2015年5月5日 下午5:16:04
     */
    @Override
    public OrderItemsDto findByItemId(String orderItemId)
    {
        OrderItems orderItems = orderItemsDao.findByItemId(orderItemId);
        OrderItemsDto orderItemsDto = BeanUtils.createBeanByTarget(orderItems, OrderItemsDto.class);
        orderItemsDto.setDate(DateUtils.format(orderItems.getDate(), DateUtils.YMD_DASH));
        return orderItemsDto;
    }

    @Override
    public void updateItemsInfo(OrderItemsDto orderItemsDto)
    {
        OrderItems orderItems = new OrderItems();
        BeanUtils.copyProperties(orderItemsDto, orderItems);
        orderItems.setOrderStatus(DataStatus.DEALS_ORDER);
        orderItemsDao.updateByPrimaryKey(orderItems);
    }

    /**
     * 
    	 * 此方法描述的是：已经预订则不能再进行预订功能
    	 * @author: cwftalus@163.com
    	 * @version: 2015年5月8日 上午9:12:11
     */
    @Override
    public List<OrderItemsDto> selectCheckBuy(List<ParameterDto> paramterList)
    {
        List<OrderItems> result = orderItemsDao.selectCheckBuy(paramterList);
        List<OrderItemsDto> dataList = BeanUtils.createBeanListByTarget(result, OrderItemsDto.class);
        return dataList;
    }

    @Override
    public List<OrderItemsDto> selectCheckSpaceBuy(List<ParameterDto> paramterList)
    {
        List<OrderItems> result = orderItemsDao.selectCheckBuy(paramterList);
        List<OrderItemsDto> dataList = BeanUtils.createBeanListByTarget(result, OrderItemsDto.class);
        return dataList;
    }

    @Override
    public void updateOrderItemRandCodeById(String randCode, String validCode, String id)
    {
        OrderItems orderItems = new OrderItems();
        orderItems.setId(id);
        orderItems.setRandCode(randCode);
        orderItems.setValidCode(validCode);
        orderItemsDao.updateByPrimaryKeySelective(orderItems);
    }

    @Override
    public int findCountItems(OrderItemsDto orderItemsDto)
    {
        return orderItemsDao.findCountsOrderItems(orderItemsDto);
    }

    /**
     * 
    	 * 此方法描述的是：完成支付返回接口 针对 订单号进行后续处理
    	 * @author: cwftalus@163.com
    	 * @version: 2015年5月12日 下午3:28:56
     */
    @Override
    public void updateOrderItemByOrderId(String orderId, int orderStatus, int paymentStatus)
    {
        // TODO Auto-generated method stub
        orderItemsDao.updateOrderItemByOrderId(orderId, orderStatus, paymentStatus);
    }

    @Override
    public void updateOrderItemByOrderNo(String orderNo, int orderStatus, int paymentStatus)
    {
        // TODO Auto-generated method stub
        orderItemsDao.updateOrderItemByOrderNo(orderNo, orderStatus, paymentStatus);
    }

    @Override
    public List<OrderItemsDto> findListByOrderNo(String orderNo)
    {
        // TODO Auto-generated method stub
        return orderItemsDao.findListByOrderNo(orderNo);
    }

    @Override
    public List<OrderItemsDto> findListByOrderId(String orderId)
    {
        // TODO Auto-generated method stub
        return orderItemsDao.findListByOrderId(orderId);
    }

    @Override
    public OrderItemsDto findByOrderNo(String orderNo)
    {
        List<OrderItems> orderItemsList = orderItemsDao.findByOrderNo(orderNo);
        if (!CollectionUtils.isEmpty(orderItemsList))
        {   
            OrderItems orderItems = orderItemsList.get(0);
            OrderItemsDto orderItemsDto = BeanUtils.createBeanByTarget(orderItems, OrderItemsDto.class);
            orderItemsDto.setDate(DateUtils.format(orderItems.getDate(), DateUtils.YMD_DASH));
            return orderItemsDto;
        }
        else
        {
            return null;
        }

    }

}
