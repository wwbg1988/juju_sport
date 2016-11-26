package com.juju.sport.home.controller.m.api;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Objects;
import com.juju.sport.base.service.ISmsSendService;
import com.juju.sport.common.alipay.util.AlipayNotify;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.common.util.RandomCode;
import com.juju.sport.order.constants.OrderStatus;
import com.juju.sport.order.constants.PaymentStatus;
import com.juju.sport.order.dto.OrderDto;
import com.juju.sport.order.dto.OrderItemsDto;
import com.juju.sport.order.service.IOrderItemsService;
import com.juju.sport.order.service.IOrderService;

@Controller
@RequestMapping(value = "/api/m")
public class AlipayMController {

	protected static final Log logger = LogFactory.getLog(AlipayMController.class);

	@Autowired
	private IOrderService iOrderService;

	@Autowired
	private IOrderItemsService iOrderItemsService;
	
	@Autowired
	private ISmsSendService iSmsSendService;

	@RequestMapping(value = "/notifyUrl.do")
	public String notifyUrl(HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException {	//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		List<OrderItemsDto> itemDtoList = new ArrayList<OrderItemsDto>();
		if(AlipayNotify.verify(params)){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码
			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——	
			if(trade_status.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
				//注意：
				//该种交易状态只在两种情况下出现
				//1、开通了普通即时到账，买家付款成功后。
				//2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
			} else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
				//注意：
				//该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
				itemDtoList = updateOrderStatus("pay",out_trade_no);
			}
			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
//			out.println("success");	//请不要修改或删除
			return "success";//
			//////////////////////////////////////////////////////////////////////////////////////////
		}else{//验证失败
			return "fail";//out.println("fail");
		}
	}
	
	public List<OrderItemsDto> updateOrderStatus(String payType,String trade_no){
		String orderType = trade_no.substring(0,1);
//		String orderNo = trade_no.substring(1);
		List<OrderItemsDto> resultList = new ArrayList<OrderItemsDto>();
		if(Objects.equal(orderType, DataStatus._ORDER_)){//主订单明细
			iOrderService.updateOrdersByOrderNo(trade_no,OrderStatus.HasPaid.getValue(),PaymentStatus.HasPaid.getValue());
			OrderDto orderDto = iOrderService.findByOrderId("",trade_no);
			iOrderItemsService.updateOrderItemByOrderId(orderDto.getId(),OrderStatus.HasPaid.getValue(),PaymentStatus.HasPaid.getValue());
			resultList = iOrderItemsService.findListByOrderId(orderDto.getId());
		}else if(Objects.equal(orderType, DataStatus._ITEMS_)){//子订单明细
			iOrderItemsService.updateOrderItemByOrderNo(trade_no,OrderStatus.HasPaid.getValue(),PaymentStatus.HasPaid.getValue());
			resultList = iOrderItemsService.findListByOrderNo(trade_no);
		}
		
		//支付成功之后短信服务
		if(payType.equals("pay")){
			for(OrderItemsDto orderItemsDto : resultList){
				String orderDay = orderItemsDto.getDate() +" "+orderItemsDto.getOrderTime()+":00";
				String validCodeNo = RandomCode.getRandomCodeByNumber(6);//随机生成6位随机数
				String formartDate = DateUtils.format(DateUtils.parse(orderDay,DateUtils.YMD_DASH_WITH_TIME), DateUtils.MDHM);
				String suffix = orderItemsDto.getTelephone().substring(7, 11);//手机号码末尾四位
				String content = "温馨提示，您已定 "+formartDate+" 篮球场（场所名字），请凭手机尾号"+suffix+"密码"+validCodeNo+"至场所售票处取票机取票。";
				String randCode = RandomCode.getEnRandomCode(1)+RandomCode.getRandomCodeByNumber(4);
				
				iSmsSendService.sendSms(0, orderItemsDto.getTelephone(), content);
				iOrderItemsService.updateOrderItemRandCodeById(randCode.toUpperCase(),validCodeNo,orderItemsDto.getId());
				
			}			
		}

		return resultList;
	}
	
	
	@RequestMapping(value = "/returnUrl.do")
	public String returnUrl(HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException {
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		//计算得出通知验证结果
		List<OrderItemsDto> itemDtoList = new ArrayList<OrderItemsDto>();
		boolean verify_result = AlipayNotify.verify(params);
		if(verify_result){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码
			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				itemDtoList = updateOrderStatus("pay",out_trade_no);
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
			}
		}else{
			//该页面可做页面美工编辑
//			out.println("验证失败");
		}
		request.setAttribute("itemDtoList", itemDtoList);
		return "/pay/success";
	}
	
//	public static void main(String[] args) {
//		String ddd = "Oddddddd";
//		System.out.println(ddd.substring(0,1));
//		System.out.println(ddd.substring(1));
//	}
}
