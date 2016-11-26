package com.juju.sport.home.controller.m.api;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.juju.sport.base.service.IUpLoadService;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.constants.SessionConstants;
import com.juju.sport.common.digest.MD5Coder;
import com.juju.sport.common.exception.SystemException;
import com.juju.sport.common.model.Response;
import com.juju.sport.home.controller.UserController;
import com.juju.sport.user.api.IUserService;
import com.juju.sport.user.constants.UserTypeEnum;
import com.juju.sport.user.dto.LoginUserDto;
import com.juju.sport.user.dto.UserAccountDto;
import com.juju.sport.user.dto.UsersDto;
import com.juju.sport.user.service.IUserVenuesInfoService;

@Controller
@RequestMapping(value = "/api/m/umanages")
public class UserMController {
	protected static final Log logger = LogFactory.getLog(UserController.class);

	@Autowired
	private IUserService iUserService;
	@Autowired
	private IUserVenuesInfoService iUserVenuesInfoService;
	
	@Autowired
	private IUpLoadService iUpLoadService;
	
	@Autowired
	private RedisTemplate<String, Integer> redisTemplate;
	
	@RequestMapping(value = "/register.do")
	@ResponseBody
	// 注册用户
	public Response<UserAccountDto> register(HttpSession session,UserAccountDto userAccountDto,@RequestParam("validCode") String validCode) {
		logger.debug("注册的用户为：" + userAccountDto.getUserAccount() + " 密码为：" + userAccountDto.getPassword());
		Response<UserAccountDto> result = new Response<UserAccountDto>();
		userAccountDto.setType(UserTypeEnum.NORMALUSER.getIndex());
		String md5oldPwd;// 获取页面上输入的密码并加密校验
		try {
			md5oldPwd = MD5Coder.encodeMD5Hex(userAccountDto.getPassword());
			userAccountDto.setPassword(md5oldPwd);
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
		
		if (iUserService.checkrepeat(userAccountDto.getUserAccount()) != 0) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("用户名已存在！");
			return result;
		}
		
		String checkCode = "";
		String keyCode = SessionConstants.PHONE_NUMBER;
		if(redisTemplate.opsForValue().get(keyCode)==null || redisTemplate.opsForValue().get(keyCode).equals("")){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证码无效");
			return result;	
		}
		
		checkCode = redisTemplate.opsForValue().get(keyCode).toString();
		if(!validCode.equals(checkCode)){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("验证码有误");
			return result;
		}
		
		

		UserAccountDto userAccount_ = iUserService.register(userAccountDto);
		if (userAccount_ == null) {
			// return JsonUtil.getJsonStr(new RequestResult(true, "对不起！注册失败！"));
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("注册失败！");
			return result;
		} else {
			LoginUserDto loginUserDto = new LoginUserDto();
			loginUserDto.setId(userAccount_.getId());
			loginUserDto.setType(userAccount_.getType());
			session.setAttribute(SessionConstants.LOGIN_USER_INFO, loginUserDto);

			UsersDto usersDto = new UsersDto();
			usersDto.setUserAccountId(userAccount_.getId());
			usersDto.setNickName(DataStatus._JUJU_);
			iUserService.insertUsers(usersDto, userAccount_.getId());
			// return JsonUtil.getJsonStr(new RequestResult(true, "恭喜您注册成功！"));
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("恭喜您注册成功！");
			return result;
		}
	}
	
	
	@RequestMapping(value = "/login.do")
	@ResponseBody
	public Response<String> login(String accountName, String pwd,
			String randomPic, HttpSession session) {
		logger.debug("user login accountName=" + accountName + " pwd=" + pwd
				+ "randomPic=" + randomPic);
		Response<String> result = new Response<String>();
		if (accountName == "" || accountName == null || pwd == "" || pwd == null) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("用户帐户名或密码不对");
			return result;
		}
		// 手机端传过来6个0
		if (!"000000".equals(randomPic)) {
			if (session.getAttribute("RandomCode") != null) {
				String randomPicSession = session.getAttribute("RandomCode")
						.toString().toLowerCase();
				if (!randomPic.toLowerCase().equals(randomPicSession)) {
					// return JsonUtil.getJsonStr(new RequestResult(true,
					// "401"));
					result.setStatus(DataStatus.HTTP_FAILE);
					result.setMessage("验证码输入不正确");
					return result;
				}
			} else {
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("验证码不存在");
				return result;
			}
		}

		String md5oldPwd;// 获取页面上输入的密码并加密校验
		try {
			md5oldPwd = MD5Coder.encodeMD5Hex(pwd);
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
		logger.debug("user login accountName=" + accountName + " pwd=" + pwd);

		UserAccountDto userAccountDto = iUserService.checkUser(accountName,
				md5oldPwd);
		if (null == userAccountDto) {
			// return JsonUtil.getJsonStr(new RequestResult(false, "403"));
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("用户名或者账号不对,请重新登录");
			return result;
		}
		
		if (!Objects.equal(userAccountDto.getType(), DataStatus.USERTYPE)) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("请使用普通用户登录操作");			
			return result;
		}
		
		LoginUserDto loginUserDto = new LoginUserDto();
		loginUserDto.setId(userAccountDto.getId());
		loginUserDto.setType(userAccountDto.getType());
		// session.removeAttribute(SessionConstants.LOGIN_USER_INFO);
		session.setAttribute(SessionConstants.LOGIN_USER_INFO, loginUserDto);

		// return JsonUtil.getJsonStr(new RequestResult(true, "200"));
		result.setData(userAccountDto.getUserAccount());
		result.setMessage(userAccountDto.getId());
		return result;
	}

}
