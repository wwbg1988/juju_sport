package com.juju.sport.home.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.juju.sport.base.service.ISmsSendService;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.dto.ParameterDto;
import com.juju.sport.common.model.Response;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.common.util.RandomCode;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.common.util.UUIDGenerator;
import com.juju.sport.home.dto.BuyDto;
import com.juju.sport.home.dto.BuyDto.PayType;
import com.juju.sport.home.dto.PayWaitDto;
import com.juju.sport.home.util.SessionUtil;
import com.juju.sport.order.constants.OrderStatus;
import com.juju.sport.order.constants.OrdersType;
import com.juju.sport.order.dto.OrderBuyDto;
import com.juju.sport.order.dto.OrderDto;
import com.juju.sport.order.dto.OrderItemsDto;
import com.juju.sport.order.dto.OrderUserDto;
import com.juju.sport.order.service.IOrderItemsService;
import com.juju.sport.order.service.IOrderMappService;
import com.juju.sport.order.service.IOrderService;
import com.juju.sport.stadium.dto.SpaceDto;
import com.juju.sport.stadium.service.ISpaceService;
import com.juju.sport.user.api.IUserService;
import com.juju.sport.user.dto.LoginUserDto;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.service.ITeamManagerService;
import com.juju.sport.user.service.IUserTeamListService;
import com.juju.sport.user.service.IUserVenuesInfoService;

/**
 * 
 * 此类描述的是：订单购买模块单独分开
 * 
 * @author: cwftalus@163.com
 * @version: 2015年3月27日 上午11:37:41
 */

@Controller
@RequestMapping(value = "/api/orderBuy")
public class OrderBuyController {
	
	@Autowired
	private IOrderService iOrderService;
	
	@Autowired
	private IOrderItemsService iOrderItemsService;
	
	@Autowired
	private ISmsSendService iSmsSendService;
	
	@Autowired
	private IUserService iUserService;
	
	@Autowired
	private ITeamManagerService iTeamManagerService;
	
	@Autowired
	private IOrderMappService iOrderMappService;
	
	@Autowired
	private IUserVenuesInfoService iUserVenuesInfoService;
	
	@Autowired
	private ISpaceService iSpaceService;
	

	@Autowired
	private IUserTeamListService iUserTeamListService;
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/buy.do")
	@ResponseBody
	public Response<BuyDto> buy(OrderBuyDto orderBuyDto,HttpSession session) throws NumberFormatException, Exception {
		Response<BuyDto> result = new Response<BuyDto>();
		BuyDto buyDto = new BuyDto();
		//获取当前登录用户Id
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		// 只允许登录用户进行操作
		if (loginUserDto == null) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("非登陆用户不允许下订单!");
			buyDto.setEnable(DataStatus.DISABLED);
			result.setData(buyDto);
			return result;
//			return JsonUtil.getJsonStr(new RequestResult(false, "非登陆用户不允许下订单!"));
		}
		String validCode = String.valueOf(session.getAttribute(DataStatus._ORDERCODE_));
		if(!orderBuyDto.getValidCode().toLowerCase().equals(validCode.toLowerCase())){
			result.setStatus(DataStatus.HTTP_FAILE);
			buyDto.setEnable(DataStatus.ENABLED);
			result.setMessage("验证码输入错误");
			result.setData(buyDto);
			return result;
		}
		
		String userAccountId = loginUserDto.getId();
		if(!StringUtils.isEmpty(userAccountId)){
			orderBuyDto.setUserAccountId(String.valueOf(userAccountId));
		}
		
		//已经预订的不能再进行预订
		String messageInfo = selectCheckBuy(orderBuyDto);
		if(!StringUtils.isEmpty(messageInfo)){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage(messageInfo);
			buyDto.setEnable(DataStatus.ENABLED);
			result.setData(buyDto);
			return result;
		}
		
		//需要判断场馆是否免费
		VenusInfoDto venusInfoDto = iUserVenuesInfoService.findByOwnerAccountId(orderBuyDto.getOwnerAccountId());
		if(venusInfoDto.getChargeType().equals(Integer.valueOf(VenusInfoDto.ChargeType.CHARGE.getInfoType()))){//收费 直接跳转到支付页面 收费 但是 价钱为0 则 直接支付成功
			buyDto.setGotoPay(PayType.pay.getInfoType());
		}else{
			result = freeAction(buyDto,orderBuyDto,userAccountId);//免费的操作处理
			if(Objects.equal(result.getStatus(), DataStatus.HTTP_FAILE)){//免费判断 当 为 500时 进行team or single 的进行选择
				return result;
			}
		}
		//
		//订单状态判断
		Integer orderStatus = OrderStatus.WaitingForPay.getValue();
		Boolean isSendNow = false;
		if(orderBuyDto.getOrderTotal()<=0){
			isSendNow = true;
			orderStatus = OrderStatus.Finished.getValue();
		}
		orderBuyDto.setOrderStatus(orderStatus);
		//主订单和订单明细数据保存数据
		orderBuyDto.setOrderNo(DataStatus._ORDER_+String.valueOf(System.currentTimeMillis()));
		orderBuyDto.setOrderDate(orderBuyDto.getOrderTime());//页面传过来是orderTime 属性 
		String orderId = iOrderService.saveOrUpdate(orderBuyDto);

		List<OrderItemsDto> orderItemsList = new ArrayList<OrderItemsDto>();
		orderItemsList = loadOrderItems(orderId,orderBuyDto);
		if(orderItemsList!=null && orderItemsList.size()>0){
			String suffix = orderBuyDto.getTelephone().substring(7, 11);//手机号码末尾四位
			for(OrderItemsDto orderItemsDto : orderItemsList){
				String orderItemId = iOrderItemsService.saveOrUpdate(orderItemsDto);
				//下单成功 需要发送短信 以下是短信接口调试成功暂时停止
				String orderDay = orderItemsDto.getDate() +" "+orderItemsDto.getOrderTime()+":00";
				String validCodeNo = RandomCode.getRandomCodeByNumber(6);//随机生成6位随机数
				String formartDate = DateUtils.format(DateUtils.parse(orderDay,DateUtils.YMD_DASH_WITH_TIME), DateUtils.MDHM);
				String content = "温馨提示，您已定 "+formartDate+" 篮球场（场所名字），请凭手机尾号"+suffix+"密码"+validCodeNo+"至场所售票处取票机取票。";
				String randCode = RandomCode.getEnRandomCode(1)+RandomCode.getRandomCodeByNumber(4);
				if(isSendNow){//需要支付的 免费的场馆 直接发送短信
					iSmsSendService.sendSms(0, orderItemsDto.getTelephone(), content);
					iOrderItemsService.updateOrderItemRandCodeById(randCode.toUpperCase(),validCodeNo,orderItemId);
				}
			}
		}
		
		buyDto.setOrderId(orderId);
		buyDto.setOrderTotal(orderBuyDto.getOrderTotal());
		result.setData(buyDto);
		result.setMessage("下单成功");
		return result;
//		return JsonUtil.getJsonStr(new RequestResult(true, "下单成功"));
	}

	
	public Response<BuyDto> freeAction(BuyDto buyDto,OrderBuyDto orderBuyDto,String userAccountId){
		Response<BuyDto> result = new Response<BuyDto>();
		//免费
		//服务器端判断是否有足球与其他场地一起预订的情况
		boolean footFlag = false;
		String referId="";
		String tempSpaces = orderBuyDto.getOrderItems();
		if(!StringUtils.isEmpty(tempSpaces)&&!"".equals(tempSpaces)){
			String[] tempA = tempSpaces.split(",");
			for(int i = 0;i<tempA.length;i++){
				String[] tempB = tempA[i].split("_");
				for(int j=0;j<tempB.length;j++){
					String tempSpaceId = tempB[0].toString();
					SpaceDto spaceDto = new SpaceDto();
					spaceDto.setId(tempSpaceId);
					List<SpaceDto> tempList = iSpaceService.findBy(spaceDto);
					if(tempList!=null&&tempList.size()>0){
						if("".equals(referId)){
							referId=tempList.get(0).getSportId();
							if("1".equals(tempList.get(0).getSportId())){
								footFlag = true;
							}
						}else{
							if(("1".equals(referId)||"1".equals(tempList.get(0).getSportId()))&&!referId.equals(tempList.get(0).getSportId())){
								result.setStatus(DataStatus.HTTP_FAILE);
								result.setMessage("不允许同时订足球与其他类型场地");
								return result;
							}
							if("1".equals(tempList.get(0).getSportId())){
								footFlag = true;
							}
						}
					}
				}
			}
		}
		

		//如果是足球场地 则跳出选择对话框
		if(footFlag){
			result.setStatus(DataStatus.HTTP_FAILE);
			buyDto.setStatus(401);
			result.setData(buyDto);
			return result;
		}
		return result;
	}
	
	/**
	 * 
		 * 此方法描述的是：判断该场地是否可以预订
		 * @author: cwftalus@163.com
		 * @version: 2015年5月8日 下午1:46:47
	 */
	@RequestMapping(value = "/checkBuy.do")
	@ResponseBody	
	public Response<String> checkBuy(OrderBuyDto orderBuyDto){
		Response<String> result = new Response<String>();
		//同一用户 同一场馆 同一场地 同一日期同一时间  同一个场馆，同一个时间段，同一个账号，可以预订两条一模一样的记录！
		String messageInfo = selectCheckBuy(orderBuyDto);
		if(!StringUtils.isEmpty(messageInfo)){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage(messageInfo);
			return result;
		}
		return result;
	}
	
	
	
	//同一用户 同一场馆 同一场地 同一日期同一时间  同一个场馆，同一个时间段，同一个账号，可以预订两条一模一样的记录！
	private String selectCheckBuy(OrderBuyDto orderBuyDto) {
		
		List<ParameterDto> parameterList = new ArrayList<ParameterDto>();

		String items[] = orderBuyDto.getOrderItems().split(",");
		List<String> orderItemList = Arrays.asList(items);

		for(int i = 0;i<orderItemList.size();i++){
			ParameterDto parameterDto = new ParameterDto();
			String itemObjs = orderItemList.get(i);
			String[] itemObj = itemObjs.split("_");
			parameterDto.setSpaceId(itemObj[0]);//场地Id
			parameterDto.setDate(orderBuyDto.getOrderTime());//选择的场地时间yyyy-mm-dd
//			orderItemsDto.setWeek(Integer.valueOf(DateUtils.dayForWeek(DateUtils.parse(orderBuyDto.getOrderTime(),DateUtils.YMD_DASH))));//订购时间 属于星期几
			parameterDto.setOrderTime(itemObj[1]);//选择的场地时间 HH
			parameterDto.setOrderStatus(OrderStatus.OrderWait.getValue());
//			parameterDto.setUserAccountId(userAccountId);//购买者的Id
			parameterList.add(parameterDto);
		}
		
		List<OrderItemsDto> dataList = iOrderItemsService.selectCheckBuy(parameterList);//
		
		StringBuffer sb = new StringBuffer();
		for(OrderItemsDto orderItemsDto : dataList){
			sb.append(orderItemsDto.getSpaceName()).append("  ").append(orderItemsDto.getOrderTime()).append(":00 - ").append(orderItemsDto.getEndTime()).append(":00  已经被预订,请重新选择");//.append("<br>");
		}
		return sb.toString();
	}

	public List<OrderItemsDto> loadOrderItems(String orderId,OrderBuyDto orderBuyDto) throws NumberFormatException, Exception{
		List<OrderItemsDto> orderItemsList = new ArrayList<OrderItemsDto>();
		String items[] = orderBuyDto.getOrderItems().split(",");
		String itemPricess[] = orderBuyDto.getItemPrices().split(",");
		String itemSpaceNames[] = orderBuyDto.getItemSpaceName().split(",");
		List<String> orderItemList = Arrays.asList(items);

		for(int i = 0;i<orderItemList.size();i++){
			OrderItemsDto orderItemsDto = new OrderItemsDto();
			String itemObjs = orderItemList.get(i);
			String[] itemObj = itemObjs.split("_");
			orderItemsDto.setOrderId(orderId);//总订单Id
			orderItemsDto.setOrderNo(DataStatus._ITEMS_+orderBuyDto.getOrderNo()+i);//子订单编码
			orderItemsDto.setSpaceId(itemObj[0]);//场地Id
			orderItemsDto.setDate(orderBuyDto.getOrderTime());//选择的场地时间yyyy-mm-dd
			orderItemsDto.setWeek(Integer.valueOf(DateUtils.dayForWeek(DateUtils.parse(orderBuyDto.getOrderTime(),DateUtils.YMD_DASH))));//订购时间 属于星期几
			orderItemsDto.setOrderTime(itemObj[1]);//选择的场地时间 HH
			orderItemsDto.setEndTime(String.valueOf(Integer.valueOf(itemObj[1])+1));//选择的结束场地时间 HH 数据冗余
			orderItemsDto.setOrderTotal(Integer.valueOf(itemPricess[i]));//订单价格
			orderItemsDto.setSpaceName(itemSpaceNames[i]);
			orderItemsDto.setUserAccountId(orderBuyDto.getUserAccountId());//购买者的Id
			orderItemsDto.setOwnerAccountId(orderBuyDto.getOwnerAccountId());//场馆用户的Id
			orderItemsDto.setTelephone(orderBuyDto.getTelephone());//购买者输入的联系电话
			orderItemsDto.setOrderStatus(orderBuyDto.getOrderStatus());//自订单的交易状态

			orderItemsList.add(orderItemsDto);
		}
		return orderItemsList;
	}
	
	public List<OrderItemsDto> teamItems(String orderId,OrderBuyDto orderBuyDto,Map<String,String> map) throws NumberFormatException, Exception{
		List<OrderItemsDto> orderItemsList = new ArrayList<OrderItemsDto>();
		String items[] = orderBuyDto.getOrderItems().split(",");
		String itemPricess[] = orderBuyDto.getItemPrices().split(",");
		String itemSpaceNames[] = orderBuyDto.getItemSpaceName().split(",");
		List<String> orderItemList = Arrays.asList(items);

		for(int i = 0;i<orderItemList.size();i++){
			OrderItemsDto orderItemsDto = new OrderItemsDto();
			String itemObjs = orderItemList.get(i);
			String[] itemObj = itemObjs.split("_");
			orderItemsDto.setOrderId(orderId);//总订单Id
			orderItemsDto.setOrderNo(DataStatus._ITEMS_+orderBuyDto.getOrderNo()+i);//子订单编码
			orderItemsDto.setSpaceId(itemObj[0]);//场地Id
			orderItemsDto.setDate(orderBuyDto.getOrderTime());//选择的场地时间yyyy-mm-dd
			orderItemsDto.setWeek(Integer.valueOf(DateUtils.dayForWeek(DateUtils.parse(orderBuyDto.getOrderTime(),DateUtils.YMD_DASH))));//订购时间 属于星期几
			orderItemsDto.setOrderTime(itemObj[1]);//选择的场地时间 HH
			orderItemsDto.setEndTime(String.valueOf(Integer.valueOf(itemObj[1])+1));//选择的结束场地时间 HH 数据冗余
			orderItemsDto.setOrderTotal(Integer.valueOf(itemPricess[i]));//订单价格
			orderItemsDto.setSpaceName(itemSpaceNames[i]);
			orderItemsDto.setUserAccountId(orderBuyDto.getUserAccountId());//购买者的Id
			orderItemsDto.setOwnerAccountId(orderBuyDto.getOwnerAccountId());//场馆用户的Id
			orderItemsDto.setTelephone(orderBuyDto.getTelephone());//购买者输入的联系电话
			if("true".equals(map.get(itemObj[0]))){
				orderItemsDto.setOrderStatus(OrderStatus.Finished.getValue());
			}else{
				orderItemsDto.setOrderStatus(orderBuyDto.getOrderStatus());//自订单的交易状态
			}

			orderItemsList.add(orderItemsDto);
		}
		return orderItemsList;
	}
	
	/**
	 * 
		 * 此方法描述的是：收费场馆转入支付页面进行支付操作
		 * @author: cwftalus@163.com
		 * @version: 2015年5月4日 下午5:27:50
	 */
	@RequestMapping(value = "/payWait.do")
	public String payWait(String orderId,String orderItemId,HttpServletRequest request){
		//2个同时没空的情况下 跳转到首页
		//orderId 	     订单
		//orderItemId 子订单
		PayWaitDto payWaitDto = new PayWaitDto();
		VenusInfoDto venusInfoDto = new VenusInfoDto();
		List<OrderItemsDto> orderItemsList = new ArrayList<OrderItemsDto>();
		OrderDto orderDto = new OrderDto();
		Date createDate = null;
		if(!StringUtils.isEmpty(orderId)){
			orderDto  = iOrderService.findByOrderId(orderId,"");
			venusInfoDto = iUserVenuesInfoService.findByOwnerAccountId(orderDto.getOwnerAccountId());//场馆用户的Id
			OrderItemsDto orderItemsDto = new OrderItemsDto();
			orderItemsDto.setOrderId(orderDto.getId());
			orderItemsList = iOrderItemsService.findListBy(orderItemsDto);
			
			createDate = orderDto.getCreateTime();

			payWaitDto.setOrderTotal(orderDto.getOrderTotal());
			payWaitDto.setOrderNo(orderDto.getOrderNo());
			payWaitDto.setDate(DateUtils.format(orderDto.getOrderTime(),DateUtils.YMD_DASH));
		}
		OrderItemsDto orderItemsDto = new OrderItemsDto();
		if(!StringUtils.isEmpty(orderItemId)){
			orderItemsDto =  iOrderItemsService.findByItemId(orderItemId);
			venusInfoDto = iUserVenuesInfoService.findByOwnerAccountId(orderItemsDto.getOwnerAccountId());//场馆用户的Id
			orderItemsList.add(orderItemsDto);
			createDate = orderItemsDto.getCreateTime();
			
			payWaitDto.setOrderTotal(orderItemsDto.getOrderTotal());
			payWaitDto.setOrderNo(orderItemsDto.getOrderNo());
			payWaitDto.setDate(orderItemsDto.getDate());
		}
		
		//判断当前时间和创建时间的时间差
		int seconds = DateUtils.secondsDiff(new Date(),createDate);
		int isdisabled = DataStatus.ENABLED;
		if(seconds > DataStatus._MAX_MINUTES){//当时就超出15M 订单处理错误
			isdisabled = DataStatus.DISABLED;
		}
		request.setAttribute("isdisabled",isdisabled);
		request.setAttribute("payWaitDto",payWaitDto);
		request.setAttribute("totalPrice",String.valueOf(payWaitDto.getOrderTotal()));
		request.setAttribute("minutes",String.valueOf((DataStatus._MAX_MINUTES-seconds)));
		request.setAttribute("orderItemsList",orderItemsList);
		request.setAttribute("venusInfoDto",venusInfoDto);
		return "/pay/index";
	}
	
	/***
	 * 此方法描述的是：选择场馆进入场地选择页面
	 * 
	 * @author: cwftalus@163.com
	 * @version: 2015年3月25日 下午5:22:15
	 * @stadium 当前场馆用户的Id
	 */
	@RequestMapping("{orderId}.do")
	public String content(HttpServletRequest request,
			@PathVariable String orderId) throws Exception{
		
		request.setAttribute("orderId", orderId);
		return "/front/orders";
	}
	/**
	 * 此方法描述的是:战队下订单
	 */
	@RequestMapping(value="/teamWait.do")
	@ResponseBody
	public Response<String> teamWait(OrderBuyDto orderBuyDto,HttpSession session) throws NumberFormatException,Exception{
		Response<String> result = new Response<String>();
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		if (loginUserDto == null) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setData("401");
			result.setMessage("非登录用户不允许下订单!");
			return result;
		}
		if(loginUserDto.getType()!=1){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setData("401");
			result.setMessage("非普通用户不允许下单!");
			return result;
		}
		
		String userAccountId=loginUserDto.getId();
			
		
		
		//case 1:有足球战队 非队长 1
		//case 2:有足球战队 是队长 不满5人  2
		//case 3:没有足球战队 0
		//case 4:所有都满足 3
		//判断用户是否已经下过订单
			
			String items[] = orderBuyDto.getOrderItems().split(",");
			List<String> orderItemStringList = Arrays.asList(items);
			Map<String,String> maps = new HashMap<String,String>();
			for(String temps:orderItemStringList){
				String splits[] = temps.split("_");
				String tempsSpace = splits[0].toString();
				SpaceDto tempSpaceDto = new SpaceDto();
				tempSpaceDto.setId(tempsSpace);
				List<SpaceDto> lists =iSpaceService.findBy(tempSpaceDto);
				int tempSpaceCount = 0;
				if(lists!=null&&lists.size()>0){
					for(SpaceDto resultSpace : lists){
						tempSpaceCount=resultSpace.getMinNumber();
						int tempResult = iTeamManagerService.findTeamMemberForOrder(userAccountId,tempSpaceCount);
						if(tempResult==1){
							result.setStatus(DataStatus.HTTP_FAILE);
							result.setData("502");
							result.setMessage("不允许预订，您不是战队队长");
							return result;
						}else if(tempResult==2){
//							result.setStatus(DataStatus.HTTP_FAILE);
//							result.setData("502");
//							result.setMessage("不允许预订，很抱歉您的战队未满场地最低人数"+tempSpaceCount+"人，您可以在个人中心-->战队管理-->分享来邀请其他人加入战队");
//							return result;
							maps.put(tempsSpace, "false");
						}else if (tempResult==0){
							result.setStatus(DataStatus.HTTP_FAILE);
							result.setData("502");
							result.setMessage("您没有加入任何足球战队，不允许预订");
							return result;
						}else if(tempResult==3){
							maps.put(tempsSpace, "true");
						}
					}
				}
				
				int tempsStart = Integer.parseInt(splits[1].toString());
				int tempsEnd = Integer.parseInt(splits[1].toString())+1;
				String orderTime =  orderBuyDto.getOrderTime();
				OrderItemsDto params = new OrderItemsDto();
				params.setOrderStatus(null);
				params.setIsOrderFail("1");
				params.setSpaceId(tempsSpace);
				params.setOrderTime(tempsStart+"");
				params.setEndTime(tempsEnd+"");
				params.setStat(DataStatus.ENABLED);
				params.setDate(orderTime);
				params.setUserAccountId(userAccountId);
				int orderTempCount = iOrderItemsService.findCountItems(params);
				if(orderTempCount>0){
					result.setStatus(DataStatus.HTTP_FAILE);
					result.setMessage("您已预订过此场地,不能重复预订!");
					result.setData("405");
					return result;
				}
			}
			
		
			orderBuyDto.setOrderStatus(OrderStatus.OrderWait.getValue());
			orderBuyDto.setUserAccountId(userAccountId);
			orderBuyDto.setOrderNo(String.valueOf(System.currentTimeMillis()));
			String orderId = iOrderService.saveOrUpdate(orderBuyDto);
			
			
			List<OrderItemsDto> orderItemsList = new ArrayList<OrderItemsDto>();
			
			orderItemsList = teamItems(orderId,orderBuyDto,maps);
			if(orderItemsList!=null && orderItemsList.size()>0){
				for(OrderItemsDto orderItemsDto : orderItemsList){
					String orderItemId = iOrderItemsService.saveOrUpdate(orderItemsDto);
					//订单加入juju_user_order_mapp表
					OrderUserDto orderUserDto = new OrderUserDto();
					orderUserDto.setStat(DataStatus.ENABLED);
					orderUserDto.setUserAccountId(userAccountId);
					orderUserDto.setOrderId(orderItemId);
					orderUserDto.setCreateTime(new Date());
					orderUserDto.setLastUpdateTime(new Date());
					orderUserDto.setId(UUIDGenerator.getUUID());
					orderUserDto.setOrdersType(OrdersType.TeamType.getValue()+"");
					iOrderMappService.insertOrder(orderUserDto);
				}
			
		}
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setData(orderId);
			return result;
	}
	
	/**
	 * 此方法描述的是：个人下订单
	 * @param orderBuyDto
	 * @param session
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/orderWait.do")
	@ResponseBody
	public Response<String> orderWait(OrderBuyDto orderBuyDto,HttpSession session) throws NumberFormatException, Exception {
		Response<String> result = new Response<String>();
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		if (loginUserDto == null) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setData("401");
			result.setMessage("非登录用户不允许下订单!");
			return result;
//			return JsonUtil.getJsonStr(new RequestResult(false, "非登陆用户不允许下订单!"));
		}
		
		if(loginUserDto.getType()!=1){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setData("401");
			result.setMessage("非普通用户不允许下单!");
			return result;
		}
		String userAccountId = loginUserDto.getId();
		if(!StringUtils.isEmpty(userAccountId)){
			orderBuyDto.setUserAccountId(String.valueOf(userAccountId));
		}
		
		//判断用户是否已经下过订单
		String items[] = orderBuyDto.getOrderItems().split(",");
		List<String> orderItemStringList = Arrays.asList(items);
		for(String temps:orderItemStringList){
			String splits[] = temps.split("_");
			String tempsSpace = splits[0].toString();
			int tempsStart = Integer.parseInt(splits[1].toString());
			int tempsEnd = Integer.parseInt(splits[1].toString())+1;
			String orderTime =  orderBuyDto.getOrderTime();
			OrderItemsDto params = new OrderItemsDto();
			params.setOrderStatus(null);
			params.setIsOrderFail("1");
			params.setSpaceId(tempsSpace);
			params.setOrderTime(tempsStart+"");
			params.setEndTime(tempsEnd+"");
			params.setStat(DataStatus.ENABLED);
			params.setDate(orderTime);
			params.setUserAccountId(userAccountId);
			int orderTempCount = iOrderItemsService.findCountItems(params);
			if(orderTempCount>0){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("您已预订过此场地,不能重复预订!");
				result.setData("405");
				return result;
			}
			
		}
		
		
		Integer orderStatus = OrderStatus.OrderWait.getValue();
		orderBuyDto.setOrderStatus(orderStatus);
		orderBuyDto.setOrderNo(String.valueOf(System.currentTimeMillis()));
		String orderId = iOrderService.saveOrUpdate(orderBuyDto);
		
		
		List<OrderItemsDto> orderItemsList = new ArrayList<OrderItemsDto>();
		
		orderItemsList = loadOrderItems(orderId,orderBuyDto);
		if(orderItemsList!=null && orderItemsList.size()>0){
			for(OrderItemsDto orderItemsDto : orderItemsList){
				String orderItemId = iOrderItemsService.saveOrUpdate(orderItemsDto);
				//订单加入juju_user_order_mapp表
				OrderUserDto orderUserDto = new OrderUserDto();
				orderUserDto.setStat(DataStatus.ENABLED);
				orderUserDto.setUserAccountId(userAccountId);
				orderUserDto.setOrderId(orderItemId);
				orderUserDto.setCreateTime(new Date());
				orderUserDto.setLastUpdateTime(new Date());
				orderUserDto.setId(UUIDGenerator.getUUID());
				orderUserDto.setOrdersType(OrdersType.PersonType.getValue()+"");
				iOrderMappService.insertOrder(orderUserDto);
			}
		
	}
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setData(orderId);
		return result;
}		
	
}
