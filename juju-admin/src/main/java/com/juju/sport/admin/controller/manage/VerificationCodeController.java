package com.juju.sport.admin.controller.manage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.common.verify.VerfiyCodeConfig;
import com.juju.sport.common.verify.VerifyCodeGenerator;

@Controller
@RequestMapping("/verify")
public class VerificationCodeController {

	private static final String VALIDATE_CODE = "validate_code";

	@ResponseBody
	@RequestMapping(value="/test.do")
	public String verify(HttpServletRequest request, String veryCode){
		String validateC = (String) request.getSession().getAttribute(VALIDATE_CODE);
		
		if(veryCode==null||"".equals(veryCode)){
			return "验证码为空";
		}else{
		if(validateC.equals(veryCode)){
			return "验证码正确";
		}else{
			return "验证码错误";
		}
		} 
	}
	
	@ResponseBody
	@RequestMapping(value="/image.do")
	public void createImage(HttpServletRequest req, HttpServletResponse resp){
		VerfiyCodeConfig config = new VerfiyCodeConfig();
		config.setHeight(35);
		config.setWidth(100);
		config.setSessionKey(VALIDATE_CODE);
		VerifyCodeGenerator.createImage(req, resp, config);
	}
}
