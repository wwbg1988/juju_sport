package com.juju.sport.home.controller.m.api;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.base.service.ISmsSendService;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.constants.SessionConstants;
import com.juju.sport.common.model.Response;
import com.juju.sport.home.util.SessionUtil;
import com.juju.sport.user.api.IUserService;
import com.juju.sport.user.dto.UsersDto;

/**
 * 此类描述的是：手机短信controller
 * 
 * @author: jam_yin
 * @version: 2015年3月31日 上午09:46
 */

@Controller
@RequestMapping(value = "/api/m/SMS")
public class SmsMController {
	/**
	 * 此方法描述的是：短信发送方法
	 * 
	 * @author: jam_yin
	 * @version: 2015年3月31日 上午09:46
	 */
	protected static final Log logger = LogFactory.getLog(SmsMController.class);

	@Autowired
	private ISmsSendService iSmsSendService;
	@Autowired
	private IUserService iUserService;
	
	@Autowired
	private RedisTemplate<String, Integer> redisTemplate;

	@RequestMapping(value = "/send.do")
	@ResponseBody
	public Response<String> send(String mobilePhone, HttpSession session,HttpServletRequest request) {
		Response<String> result = new Response<String>();
		int randomNumber = (int) (Math.random() * 9000 + 1000);
		String content = "温馨提示，为了保护您的隐私，请您在90秒内输入" + randomNumber + "验证码。";// 短信内容
		
		iSmsSendService.sendSms(randomNumber, mobilePhone, content);

		String keyCode = SessionConstants.PHONE_NUMBER;
		redisTemplate.opsForValue().set(keyCode, randomNumber, 90, TimeUnit.SECONDS);;
		
//		session.setAttribute(DataStatus._ORDERCODE_, randomNumber);
//		session.setMaxInactiveInterval(90); // 设置session有效期为90秒
		//return JsonUtil.getJsonStr(new RequestResult(true, returnString + " " + randomNumber));
		result.setStatus(DataStatus.HTTP_SUCCESS);
		return result;
	}

}
