package com.juju.sport.admin.controller.manage;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.admin.manager.service.IAdminAccountLoginService;
import com.juju.sport.admin.manger.dto.AdminUserLoginInfoDto;
import com.juju.sport.common.constants.SessionConstants;
import com.juju.sport.common.digest.MD5Coder;
import com.juju.sport.common.model.RequestResult;

/**
 * 
 * @author rkzhang
 *
 */
@Controller
@RequestMapping(value="/login")
public class AdminLoginController {
	
	protected static final Log logger = LogFactory.getLog(AdminLoginController.class);
	
	@Autowired
	private IAdminAccountLoginService loginService;

	@RequestMapping(value="/index.do")
	public String index(){
		logger.debug("visit index page");
		return "/login";
	}
	
	@RequestMapping(value="/main.do")
	public String main(){
		logger.debug("visit home page");
		return "/admin-home";
	}
	
	@RequestMapping(value="/login.do")
	@ResponseBody
	public RequestResult login(@RequestParam("userCode")String userCode, @RequestParam("password")String password, HttpSession session){
		logger.debug("user loging username=" + userCode + " passwd=" + password);
		String md5passwd = "";
		try {
			md5passwd = MD5Coder.encodeMD5Hex(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AdminUserLoginInfoDto user = loginService.findAdminUserLoginInfo(userCode);
		if(user == null){
			return new RequestResult(false, "用户不存在");
		}else if(!md5passwd.equals(user.getPasswd())){
			return new RequestResult(false, "密码错误");
		}
		session.setAttribute(SessionConstants.ADMIN_USER_ID, user);
		return new RequestResult(true, "登录成功");
	}
	
	@RequestMapping(value="/getUser.do")
	@ResponseBody
	public AdminUserLoginInfoDto getUser(HttpSession session){
		AdminUserLoginInfoDto user = (AdminUserLoginInfoDto)session.getAttribute(SessionConstants.ADMIN_USER_ID);
		return user;
	}
}
