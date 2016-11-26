package com.juju.sport.home.controller.m.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.juju.sport.base.service.ISmsSendService;
import com.juju.sport.common.alipay.config.AlipayConfig;
import com.juju.sport.common.alipay.m.SignUtils;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.dto.ParameterDto;
import com.juju.sport.common.model.Response;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.common.util.JsonUtil;
import com.juju.sport.common.util.PropertiesUtils;
import com.juju.sport.common.util.RandomCode;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.home.controller.UploadController;
import com.juju.sport.home.dto.BuyDto;
import com.juju.sport.home.dto.BuyDto.PayType;
import com.juju.sport.home.util.SessionUtil;
import com.juju.sport.order.constants.OrderStatus;
import com.juju.sport.order.dto.BuyOrderDto;
import com.juju.sport.order.dto.BuyOrderItemsDto;
import com.juju.sport.order.dto.OrderBuyDto;
import com.juju.sport.order.dto.OrderItemsDto;
import com.juju.sport.order.service.IOrderItemsService;
import com.juju.sport.order.service.IOrderMappService;
import com.juju.sport.order.service.IOrderService;
import com.juju.sport.stadium.dto.SpaceDto;
import com.juju.sport.stadium.dto.SpaceOpenTimeDto;
import com.juju.sport.stadium.service.ISpaceOpenTimeService;
import com.juju.sport.stadium.service.ISpaceService;
import com.juju.sport.user.api.IUserService;
import com.juju.sport.user.dto.LoginUserDto;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.service.ITeamManagerService;
import com.juju.sport.user.service.IUserVenuesInfoService;

@Controller
@RequestMapping(value = "/api/m/orderBuy")
public class OrderBuyMController {
	
	protected static final Log logger = LogFactory.getLog(OrderBuyMController.class);
	
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
	private ISpaceOpenTimeService iSpaceOpenTimeService;
	
	private HashMap<String,Integer> spacePriceMap = null;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/buy.do")
	@ResponseBody
	public Response<BuyDto> buy(String opt,HttpSession session) throws NumberFormatException, Exception {
		Response<BuyDto> result = new Response<BuyDto>();
		BuyDto buyDto = new BuyDto();
		//获取当前登录用户Id
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		// 只允许登录用户进行操作
		if (loginUserDto == null) {
			buyDto.setEnable(DataStatus.DISABLED);
			return printResult(result, DataStatus.HTTP_FAILE,"验证码输入错误",buyDto);
		}

		BuyOrderDto buyOrderDto = JsonUtil.getObjFromJson(opt,BuyOrderDto.class);

		//短信验证码暂时取消
//		String validCode = String.valueOf(session.getAttribute(DataStatus._ORDERCODE_));
//		if(!buyOrderDto.getValidCode().toLowerCase().equals(validCode.toLowerCase())){
//			buyDto.setEnable(DataStatus.ENABLED);
//			return printResult(result, DataStatus.HTTP_FAILE,"验证码输入错误",buyDto);
//		}

		String userAccountId = loginUserDto.getId();
		if(!StringUtils.isEmpty(userAccountId)){
			buyOrderDto.setUserAccountId(String.valueOf(userAccountId));
		}

		//已经预订的不能再进行预订	
		String messageInfo = selectCheckBuy(buyOrderDto);		
		if(!StringUtils.isEmpty(messageInfo)){
			buyDto.setEnable(DataStatus.ENABLED);
			return printResult(result, DataStatus.HTTP_FAILE,messageInfo,buyDto);
		}
		//需要判断场馆是否免费
		VenusInfoDto venusInfoDto = iUserVenuesInfoService.findByOwnerAccountId(buyOrderDto.getOwnerAccountId());
		if(venusInfoDto.getChargeType().equals(Integer.valueOf(VenusInfoDto.ChargeType.CHARGE.getInfoType()))){//收费 直接跳转到支付页面 收费 但是 价钱为0 则 直接支付成功
			buyDto.setGotoPay(PayType.pay.getInfoType());
		}else{
			result = freeAction(buyDto,buyOrderDto,userAccountId);//免费的操作处理
			if(Objects.equal(result.getStatus(), DataStatus.HTTP_FAILE)){//免费判断 当 为 500时 进行team or single 的进行选择
				return result;
			}
		}
		//计算总价格场地对应价格
		List<ParameterDto> paramterList = getParameterList(buyOrderDto);
		List<SpaceOpenTimeDto> spaceOpenTimePriceList = iSpaceOpenTimeService.querySpaceTimePrice(paramterList);
		Integer orderTotal = 0;

		spacePriceMap = new HashMap<String,Integer>();
		for(SpaceOpenTimeDto spaceOpenTimeDto: spaceOpenTimePriceList){
			String start = DateUtils.format(spaceOpenTimeDto.getStartTime(),DateUtils.HH);
			String end = DateUtils.format(spaceOpenTimeDto.getEndTime(),DateUtils.HH);
			Integer startI = Integer.valueOf(start);
			Integer endI = Integer.valueOf(end);
			for(int s = startI;s<endI;s++){
				String keyCode = spaceOpenTimeDto.getSpaceId()+"-"+spaceOpenTimeDto.getOpenWeek()+"-"+String.valueOf(s);
				spacePriceMap.put(keyCode,spaceOpenTimeDto.getPrice());
			}
		}

		for(ParameterDto paramObj : paramterList){
			String keyCode = paramObj.getSpaceId()+"-"+paramObj.getWeek()+"-"+Integer.valueOf(paramObj.getOrderTime());
			if(spacePriceMap.containsKey(keyCode)){
				orderTotal+=spacePriceMap.get(keyCode);	
			}
		}
		
		//订单状态判断
		Integer orderStatus = OrderStatus.WaitingForPay.getValue();
		Boolean isSendNow = false;
		if(orderTotal <= 0){
			isSendNow = true;
			orderStatus = OrderStatus.Finished.getValue();
		}
		//主订单数据保存属性对应
		OrderBuyDto orderBuyDto = CopyMainOrder(orderStatus,orderTotal,buyOrderDto);//new OrderBuyDto();

		//主订单和订单明细数据保存数据
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
				if(isSendNow){
					iSmsSendService.sendSms(0, orderItemsDto.getTelephone(), content);
				}
				iOrderItemsService.updateOrderItemRandCodeById(randCode.toUpperCase(),validCodeNo,orderItemId);				
			}
		}
		
		String payInfo = m_pay(orderBuyDto.getOrderNo(), String.valueOf(orderTotal));
		logger.debug("payInfo"+payInfo);
		buyDto.setPayInfo(payInfo);
		buyDto.setOrderId(orderId);
		buyDto.setOrderTotal(orderBuyDto.getOrderTotal());		
		return printResult(result, DataStatus.HTTP_SUCCESS,"下单成功",buyDto);
	}
	
	public Response<BuyDto> printResult(Response<BuyDto> result,int status,String messages,BuyDto buyDto){
		result.setStatus(status);
		result.setMessage(messages);

		result.setData(buyDto);
		return result;
	}
	
	public String m_pay(String orderNo,String price){
		logger.debug(price+"orderInfo"+orderNo);
		// 订单
		String orderInfo = getOrderInfo(orderNo,orderNo, orderNo, price);
		logger.debug("orderInfo"+orderInfo);
		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		logger.debug("sign"+sign);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();
		return payInfo;
	}
	
	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}
	
	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String orderNo,String subject, String body, String price) {
		// 签约合作者身份ID
				String orderInfo = "partner=" + "\"" + AlipayConfig.partner + "\"";
				// 签约卖家支付宝账号
				orderInfo += "&seller_id=" + "\"" + AlipayConfig.seller_email + "\"";
				// 商户网站唯一订单号
				orderInfo += "&out_trade_no=" + "\"" + orderNo + "\"";
				// 商品名称
				orderInfo += "&subject=" + "\"" + orderNo + "\"";
				// 商品详情
				orderInfo += "&body=" + "\"" + orderNo + "\"";
				// 商品金额
				orderInfo += "&total_fee=" + "\"" + price + "\"";
				// 服务器异步通知页面路径
				orderInfo += "&notify_url=" + "\"" + PropertiesUtils.getProperty("app_notify_url")	+ "\"";
				// 服务接口名称， 固定值
				orderInfo += "&service=\"mobile.securitypay.pay\"";
				// 支付类型， 固定值
				orderInfo += "&payment_type=\"1\"";
				// 参数编码， 固定值
				orderInfo += "&_input_charset=\"utf-8\"";
				// 设置未付款交易的超时时间
				// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
				// 取值范围：1m～15d。
				// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
				// 该参数数值不接受小数点，如1.5h，可转换为90m。
				orderInfo += "&it_b_pay=\"30m\"";
				// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
				// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
				// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
				orderInfo += "&return_url=\"m.alipay.com\"";
				// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
				// orderInfo += "&paymethod=\"expressGateway\"";
		
//		// 签约合作者身份ID
//		String orderInfo = "partner=" + AlipayConfig.partner + "";
//		// 签约卖家支付宝账号
//		orderInfo += "&seller_id=" + AlipayConfig.seller_email + "";
//		// 商户网站唯一订单号
//		orderInfo += "&out_trade_no=" + orderNo + "";
//		// 商品名称
//		orderInfo += "&subject=" + subject + "";
//		// 商品详情
//		orderInfo += "&body="  + body + "";
//		// 商品金额
//		orderInfo += "&total_fee=" + price + "";
//		// 服务器异步通知页面路径
//		orderInfo += "&notify_url=" + PropertiesUtils.getProperty("app_notify_url")	+ "";
//		// 服务接口名称， 固定值
//		orderInfo += "&service=mobile.securitypay.pay";
//		// 支付类型， 固定值
//		orderInfo += "&payment_type=1";
//		// 参数编码， 固定值
//		orderInfo += "&_input_charset=utf-8";
//		// 设置未付款交易的超时时间
//		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
//		// 取值范围：1m～15d。
//		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
//		// 该参数数值不接受小数点，如1.5h，可转换为90m。
//		orderInfo += "&it_b_pay=30m";
//		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
//		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
//		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//		orderInfo += "&return_url=m.alipay.com";
//		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
//		// orderInfo += "&paymethod=\"expressGateway\"";
		return orderInfo;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, AlipayConfig.appKey);
	}
	
	public OrderBuyDto CopyMainOrder(Integer orderStatus,Integer orderTotal,BuyOrderDto buyOrderDto){
		OrderBuyDto orderBuyDto = new OrderBuyDto();
		orderBuyDto.setOrderStatus(orderStatus);
		orderBuyDto.setOrderTotal(orderTotal);
		orderBuyDto.setOrderNo(DataStatus._ORDER_+String.valueOf(System.currentTimeMillis()));
		
		orderBuyDto.setUserAccountId(buyOrderDto.getUserAccountId());
		orderBuyDto.setOwnerAccountId(buyOrderDto.getOwnerAccountId());
		orderBuyDto.setTelephone(buyOrderDto.getOrderTel());
		orderBuyDto.setOrderDate(buyOrderDto.getOrderDate());
		
		orderBuyDto.setOrders(buyOrderDto.getOrders());
		return orderBuyDto;
	}
	
	public String sendOrderCheck(String mobilePhone, HttpSession session) {
		Response<String> result = new Response<String>();
		int randomNumber = (int) (Math.random() * 9000 + 1000);
		String content = "温馨提示，为了保护您的隐私，请您在90秒内输入" + randomNumber + "验证码。";// 短信内容
		iSmsSendService.sendSms(randomNumber, mobilePhone, content);
		session.setAttribute(DataStatus._ORDERCODE_, String.valueOf(randomNumber));
		session.setMaxInactiveInterval(90); // 设置session有效期为90秒
		return String.valueOf(randomNumber);
	}
	
	
	//sql 条件
	public List<ParameterDto> getParameterList(BuyOrderDto buyOrderDto){
		List<ParameterDto> parameterList = new ArrayList<ParameterDto>();
		for(BuyOrderItemsDto orderItemsDto : buyOrderDto.getOrders()){
			ParameterDto parameterDto = new ParameterDto();
			parameterDto.setSpaceId(orderItemsDto.getSpaceId());//场地Id
			parameterDto.setDate(buyOrderDto.getOrderDate());//选择的场地时间yyyy-mm-dd
			try {
				parameterDto.setWeek(Integer.valueOf(DateUtils.dayForWeek(DateUtils.parse(buyOrderDto.getOrderDate(),DateUtils.YMD_DASH))));//订购时间 属于星期几
			} catch (Exception e) {
			}
			parameterDto.setOrderTime(orderItemsDto.getOrderTime().split(":")[0]);//选择的场地时间 HH
			parameterDto.setOrderStatus(OrderStatus.OrderWait.getValue());
			parameterDto.setUserAccountId(buyOrderDto.getUserAccountId());//购买者的Id
			parameterList.add(parameterDto);			
		}
		return parameterList;
	}
	
	//同一用户 同一场馆 同一场地 同一日期同一时间  同一个场馆，同一个时间段，同一个账号，可以预订两条一模一样的记录！
	private String selectCheckBuy(BuyOrderDto buyOrderDto) {
		List<ParameterDto> parameterList = new ArrayList<ParameterDto>();

		parameterList = getParameterList(buyOrderDto);

		StringBuffer sb = new StringBuffer();			
		List<OrderItemsDto> resultList = iOrderItemsService.selectCheckBuy(parameterList);
		for(OrderItemsDto orderItemsDto : resultList){
			sb.append(orderItemsDto.getSpaceName()).append("  ").append(orderItemsDto.getOrderTime()).append(":00 - ").append(orderItemsDto.getEndTime()).append(":00  已经被预订,请重新选择").append("<br>");
		}
		return sb.toString();
	}
	
	public List<OrderItemsDto> loadOrderItems(String orderId,OrderBuyDto orderBuyDto) throws NumberFormatException, Exception{
		List<OrderItemsDto> orderItemsList = new ArrayList<OrderItemsDto>();
		int i = 1;
		for(BuyOrderItemsDto buyOrderItemsDto : orderBuyDto.getOrders()){
			OrderItemsDto orderItemsDto = new OrderItemsDto();
			orderItemsDto.setOrderId(orderId);//总订单Id
			orderItemsDto.setOrderNo(DataStatus._ITEMS_+orderBuyDto.getOrderNo()+i);//子订单编码
			orderItemsDto.setSpaceId(buyOrderItemsDto.getSpaceId());//场地Id
			orderItemsDto.setDate(orderBuyDto.getOrderDate());//选择的场地时间yyyy-mm-dd
			orderItemsDto.setWeek(Integer.valueOf(DateUtils.dayForWeek(DateUtils.parse(orderBuyDto.getOrderDate(),DateUtils.YMD_DASH))));//订购时间 属于星期几
			String orderTime = buyOrderItemsDto.getOrderTime().split(":")[0];
			orderItemsDto.setOrderTime(orderTime);//选择的场地时间 HH
			orderItemsDto.setEndTime(String.valueOf(Integer.valueOf(orderTime)+1));//选择的结束场地时间 HH 数据冗余
			String keyCode = buyOrderItemsDto.getSpaceId()+"-"+orderTime;
			Integer itemOrderTotal = spacePriceMap.get(keyCode);
			orderItemsDto.setOrderTotal(itemOrderTotal);//订单价格
			orderItemsDto.setSpaceName(buyOrderItemsDto.getSpaceName());//场地名称
			orderItemsDto.setUserAccountId(orderBuyDto.getUserAccountId());//购买者的Id
			orderItemsDto.setOwnerAccountId(orderBuyDto.getOwnerAccountId());//场馆用户的Id
			orderItemsDto.setTelephone(orderBuyDto.getTelephone());//购买者输入的联系电话
			orderItemsDto.setOrderStatus(orderBuyDto.getOrderStatus());//自订单的交易状态

			orderItemsList.add(orderItemsDto);	
			i++;
		}

		return orderItemsList;
	}
	
	public Response<BuyDto> freeAction(BuyDto buyDto,BuyOrderDto buyOrderDto,String userAccountId){
		Response<BuyDto> result = new Response<BuyDto>();
		//免费
		//服务器端判断是否有足球与其他场地一起预订的情况
		boolean footFlag = false;
		String referId="";
		
		for(BuyOrderItemsDto  buyOrderItemsDto : buyOrderDto.getOrders()){
			SpaceDto spaceDto = new SpaceDto();
			spaceDto.setId(buyOrderItemsDto.getSpaceId());
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

		//如果是足球场地 则跳出选择对话框
		if(footFlag){
			result.setStatus(DataStatus.HTTP_FAILE);
			buyDto.setStatus(401);
			result.setData(buyDto);
			return result;
		}
		return result;
	}
}
