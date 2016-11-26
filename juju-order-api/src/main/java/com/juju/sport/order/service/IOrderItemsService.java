package com.juju.sport.order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.juju.sport.common.dto.ParameterDto;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.order.dto.OrderItemsDto;

/**
 * 
 * 此类描述的是：订单详细页面数据
 * 
 * @author: cwftalus@163.com
 * @version: 2015年3月25日 上午10:33:05
 */
@Service
public interface IOrderItemsService {
	PageResult<OrderItemsDto> findBy(OrderItemsDto orderItemsDto,PageQuery page);
	
	int findCountItems(OrderItemsDto orderItemsDto);
	
	int findCount(OrderItemsDto orderItemsDto);
	
	/**
	 * 按用户ID查询用户订单
	 * @author: jam
	 * @version: 2015年4月11日 下午14:16:16
	 */
	List<OrderItemsDto> findOrderByUserAccountId(String userAccountId);
	
	/**
	 * 
		 * 此方法描述的是：订单详细的数据保存
		 * @author: cwftalus@163.com
		 * @version: 2015年3月30日 下午3:54:05
	 */
	public void saveOrUpdate(List<OrderItemsDto> orderItemsList);
	/**
	 * 
		 * 此方法描述的是：订单详细的数据保存
		 * @author: cwftalus@163.com
		 * @version: 2015年3月30日 下午3:54:05
	 */
	public String saveOrUpdate(OrderItemsDto orderItemsDto);
	/**
	 * 
		 * 此方法描述的是：根据条件查询订单详情信息
		 * @author: cwftalus@163.com
		 * @version: 2015年3月31日 上午10:10:26
	 */
	List<OrderItemsDto> findListBy(OrderItemsDto orderItemsDto);
	
	int findOrderCount(OrderItemsDto orderItemsDto);
	
	/**
	 * 
		 * 此方法描述的是：根据子订单Id 查询  自订单信息
		 * @author: cwftalus@163.com
		 * @version: 2015年5月5日 下午5:16:04
	 */
	OrderItemsDto findByItemId(String orderItemId);
	
	/**
	 *子订单修改 
	 */
	void updateItemsInfo(OrderItemsDto orderItemsDto);
	/**
	 * 
		 * 此方法描述的是：已经预订则不能再进行预订功能
		 * @author: cwftalus@163.com
		 * @version: 2015年5月8日 上午9:12:11
	 */
	List<OrderItemsDto> selectCheckBuy(List<ParameterDto> paramterList);
	
	List<OrderItemsDto> selectCheckSpaceBuy(List<ParameterDto> paramterList);
	
	/**
	 * 
		 * 此方法描述的是：根据Id 修改 randCode
		 * @author: cwftalus@163.com
		 * @version: 2015年5月8日 下午4:02:52
	 */
	void updateOrderItemRandCodeById(String randCode, String validCode,String id);

	/**
	 * 
		 * 此方法描述的是：完成支付返回接口 针对 订单号进行后续处理
		 * @author: cwftalus@163.com
		 * @version: 2015年5月12日 下午3:28:56
	 */
	void updateOrderItemByOrderId(String orderId,int orderStatus,int paymentStatus);
	/**
	 * 
		 * 此方法描述的是：完成支付返回接口 针对 订单号进行后续处理
		 * @author: cwftalus@163.com
		 * @version: 2015年5月12日 下午3:28:56
	 */
	void updateOrderItemByOrderNo(String orderNo,int orderStatus,int paymentStatus);
	/**
	 * 
		 * 此方法描述的是：根据orderNo 查询接口
		 * @author: cwftalus@163.com
		 * @version: 2015年5月12日 下午3:28:56
	 */
	List<OrderItemsDto> findListByOrderNo(String orderNo);
	
	/**
	 * 
		 * 此方法描述的是：根据orderNo 查询接口
		 * @author: cwftalus@163.com
		 * @version: 2015年5月12日 下午3:28:56
	 */
	List<OrderItemsDto> findListByOrderId(String orderId);

    
    /**     
     * findByOrderNo：根据订单编号查询订单详情
     * @param orderNo 订单标号
     * @return OrderItemsDto 订单详情
     * @author Vincent
     * @date 2015年5月26日 下午6:02:57	 
     */
    OrderItemsDto findByOrderNo(String orderNo);
	
}
