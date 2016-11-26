package com.juju.sport.home.controller;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.juju.sport.common.tools.RandomPicTools;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.common.util.UUIDGenerator;
import com.juju.sport.home.util.SessionUtil;
import com.juju.sport.user.api.IUserService;
import com.juju.sport.user.constants.UserTypeEnum;
import com.juju.sport.user.dto.LoginUserDto;
import com.juju.sport.user.dto.UserAccountDto;
import com.juju.sport.user.dto.UsersDto;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.service.IUserVenuesInfoService;

@Controller
@RequestMapping(value = "/umanages")
public class UserController {
	protected static final Log logger = LogFactory.getLog(UserController.class);

	@Autowired
	private IUserService iUserService;
	@Autowired
	private IUserVenuesInfoService iUserVenuesInfoService;
	
	@Autowired
	private IUpLoadService iUpLoadService;

	@RequestMapping(value = "/register.do")
	@ResponseBody
	// 注册用户
	public Response<UserAccountDto> register(HttpSession session,
			UserAccountDto userAccountDto,@RequestParam("randomPic") String randomPic) {
		logger.debug("注册的用户为：" + userAccountDto.getUserAccount() + " 密码为："
				+ userAccountDto.getPassword());
		Response<UserAccountDto> result = new Response<UserAccountDto>();
		userAccountDto.setType(UserTypeEnum.NORMALUSER.getIndex());
		String md5oldPwd;// 获取页面上输入的密码并加密校验
		try {
			md5oldPwd = MD5Coder.encodeMD5Hex(userAccountDto.getPassword());
			userAccountDto.setPassword(md5oldPwd);
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
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
					result.setMessage("验证码输入错误！");
					return result;
				}
			} else {
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("验证码不存在！");
				return result;
			}
		}
		
		if (iUserService.checkrepeat(userAccountDto.getUserAccount()) != 0) {
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("用户名已存在！");
			return result;
		}
		UserAccountDto userAccount_ = iUserService.register(userAccountDto);
		if (userAccount_ == null) {
			// return JsonUtil.getJsonStr(new RequestResult(true, "对不起！注册失败！"));
			result.setStatus(DataStatus.HTTP_SUCCESS);
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

	@RequestMapping(value = "/checkrepeat.do")
	@ResponseBody
	// 检查用户名是否重复
	public Response<String> checkrepeat(UserAccountDto userAccountDto) {
		Response<String> result = new Response<String>();
		if (iUserService.checkrepeat(userAccountDto.getUserAccount()) == 0) {
			// return JsonUtil.getJsonStr(new RequestResult(true, "pass"));
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setData("pass");
			return result;
		} else {
			// return JsonUtil.getJsonStr(new RequestResult(true, "nopass"));
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setData("nopass");
			return result;
		}
	}

	@RequestMapping(value = "/addOrUpdateUserInfo.do")
	@ResponseBody
	// 修改用户基础信息（非账户信息）
	public Response<String> addOrUpdateUserInfo(HttpSession session,
			UsersDto usersDto) {
		// LoginUserDto loginUserDto = new LoginUserDto();
		// String userAccountId =
		// (String)session.getAttribute(loginUserDto.getId());
		Response<String> result = new Response<String>();
		String userAccountId = "";
		if (SessionUtil.getLoginSession(session) == null) {
			// return JsonUtil.getJsonStr(new RequestResult(true,
			// "用户没有登陆或登陆失效！"));
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("用户未登陆或登陆失效！");
			return result;
		}
		userAccountId = SessionUtil.getLoginSession(session).getId();
		if (!StringUtils.isEmpty(userAccountId)) {
			int flag = 0;
			UsersDto oldusersDto = iUserService
					.selectUsersByUserAccountId(userAccountId);
			if (oldusersDto != null) {// 修改
				usersDto.setId(oldusersDto.getId());
				usersDto.setUserAccountId(userAccountId);
				flag = iUserService.updateUsers(usersDto);
			} else {// 新增
				flag = iUserService.insertUsers(usersDto, userAccountId);
			}
			if (flag > 0) {
				// return JsonUtil.getJsonStr(new RequestResult(true,
				// "用户信息保存成功"));
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setMessage("保存成功！");
				return result;
			}
		}
		// return JsonUtil.getJsonStr(new RequestResult(false, "用户信息保存失败"));
		//result.setStatus(DataStatus.HTTP_SUCCESS);
		return result;
	}

	@RequestMapping(value = "/modifyPwd.do")
	@ResponseBody
	public Response<String> modifyPwd(
			@RequestParam("oldpassword") String oldpassword,
			@RequestParam("newpassword") String newpassword, HttpSession session) {
		Response<String> result = new Response<String>();
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		if (loginUserDto == null) {
			// return JsonUtil.getJsonStr(new RequestResult(true,
			// "用户没有登陆或登陆失效！"));
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("用户未登陆或登陆失效！");
			return result;
		}
		String md5NewPwd = null;
		if (!StringUtils.isEmpty(oldpassword)) {
			try {
				md5NewPwd = MD5Coder.encodeMD5Hex(oldpassword);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		UserAccountDto userAccountDto = iUserService
				.selectUserAccountByUserId(loginUserDto.getId());
		if (!userAccountDto.getPassword().equals(md5NewPwd)) {
			// return JsonUtil.getJsonStr(new RequestResult(true, "200"));
			// //原密码输入正确
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("原密码输入错误");
			return result;
		}

		if (!StringUtils.isEmpty(newpassword)) {
			try {
				if (MD5Coder.encodeMD5Hex(newpassword).equals(md5NewPwd)) {
					result.setStatus(DataStatus.HTTP_FAILE);
					result.setMessage("原密码和新密码一致！");
					return result;
				}
				userAccountDto.setPassword(MD5Coder.encodeMD5Hex(newpassword));
			} catch (Exception e) {
				throw new SystemException(e.getMessage(), e);
			}
			int flag = iUserService.updateUserAccount(userAccountDto);
			if (flag > 0) {
				// return JsonUtil.getJsonStr(new RequestResult(true,
				// "修改密码成功"));
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setMessage("修改密码成功！");
			} else {
				// return JsonUtil.getJsonStr(new RequestResult(false,
				// "修改密码失败"));
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("修改密码失败！");
			}
		}

		return result;
	}

	@RequestMapping(value = "/check_OPW.do")
	@ResponseBody
	// 修改用户密码时检查输入的原密码是否正确
	public Response<String> check_OPW(
			@RequestParam("oldpassword") String oldpassword, HttpSession session) {
		logger.debug("oldpassword =" + oldpassword);
		Response<String> result = new Response<String>();
		String md5NewPwd = null;
		if (!StringUtils.isEmpty(oldpassword)) {
			try {
				md5NewPwd = MD5Coder.encodeMD5Hex(oldpassword);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// LoginUserDto loginUserDto =(LoginUserDto)
		// session.getAttribute(SessionConstants.LOGIN_USER_INFO);
		UserAccountDto userAccountDto = null;
		if (SessionUtil.getLoginSession(session) == null) {
			// return JsonUtil.getJsonStr(new RequestResult(true,
			// "用户没有登陆或登陆失效！"));
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("用户未登陆或登陆失效！");
			return result;
		}
		userAccountDto = iUserService.selectUserAccountByUserId(SessionUtil
				.getLoginSession(session).getId());
		if (userAccountDto.getPassword().equals(md5NewPwd)) {
			// return JsonUtil.getJsonStr(new RequestResult(true, "200"));
			// //原密码输入正确
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("200");
			return result;
		} else {
			// return JsonUtil.getJsonStr(new RequestResult(true, "406"));
			// //原密码输入错误
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("406");
			return result;
		}
	}

	// @RequestMapping(value="/updatepwd.do")
	// @ResponseBody
	// //修改密码
	// public Response<String> updatepwd(@RequestParam("oldpassword")String
	// oldpassword,@RequestParam("newpassword")String newpassword, HttpSession
	// session) {
	// logger.debug("user loging oldpassword=" + oldpassword + " newpassword=" +
	// newpassword);
	// //LoginUserDto loginUserDto = new LoginUserDto();
	// //String userAccountId = (String)
	// session.getAttribute(loginUserDto.getId());
	// Response<String> result = new Response<String>();
	// String userAccountId = null;
	// if(SessionUtil.getLoginSession(session) == null){
	// //return JsonUtil.getJsonStr(new RequestResult(true, "用户没有登陆或登陆失效！"));
	// result.setStatus(DataStatus.HTTP_SUCCESS);
	// result.setMessage("用户未登陆或登录过期！");
	// return result;
	// }
	// userAccountId = SessionUtil.getLoginSession(session).getId();
	// String md5oldPwd;//获取页面上输入的旧密码并加密校验
	// try {
	// md5oldPwd = MD5Coder.encodeMD5Hex(oldpassword);
	// } catch (Exception e) {
	// throw new SystemException(e.getMessage(), e);
	// }
	//
	// UserAccountDto userAccountDto =
	// iUserService.selectUserAccountByUserId(userAccountId);
	// if (userAccountDto == null) {
	// //return JsonUtil.getJsonStr(new RequestResult(false, "用户未登录或登录过期"));
	// result.setStatus(DataStatus.HTTP_SUCCESS);
	// result.setMessage("用户未登录或登录过期！");
	// return result;
	// }
	//
	// if(userAccountDto.getPassword().equals(md5oldPwd)){
	// if(!StringUtils.isEmpty(newpassword)){
	// try {
	// userAccountDto.setPassword(MD5Coder.encodeMD5Hex(newpassword));
	// } catch (Exception e) {
	// throw new SystemException(e.getMessage(),e);
	// }
	// int flag = iUserService.updateUserAccount(userAccountDto);
	// if(flag>0){
	// //return JsonUtil.getJsonStr(new RequestResult(true, "修改密码成功"));
	// result.setStatus(DataStatus.HTTP_SUCCESS);
	// result.setMessage("修改密码成功！");
	// return result;
	// }else{
	// //return JsonUtil.getJsonStr(new RequestResult(false, "修改密码失败"));
	// result.setStatus(DataStatus.HTTP_SUCCESS);
	// result.setMessage("修改密码失败！");
	// return result;
	// }
	// }
	// }
	// //return JsonUtil.getJsonStr(new RequestResult(false, "用户密码输入错误"));
	// result.setStatus(DataStatus.HTTP_SUCCESS);
	// result.setMessage("原密码错误！");
	// return result;
	// }

	@RequestMapping(value = "/showUserInfo.do")
	@ResponseBody
	// 回显用户基本信息
	public Response<UsersDto> showUserInfo(HttpSession session) {
		// Gson gson = new Gson();
		Response<UsersDto> response = new Response<UsersDto>();
		String userAccountId = null;
		if (SessionUtil.getLoginSession(session) == null) {
			// return JsonUtil.getJsonStr(new RequestResult(true,
			// "用户没有登陆或登陆失效！"));
			response.setMessage("用户没有登陆或登陆失效！");
			return response;

		}
		userAccountId = SessionUtil.getLoginSession(session).getId();
		UsersDto userDto = iUserService
				.selectUsersByUserAccountId(userAccountId);
		if (userDto != null) {
			response.setData(userDto);
			return response;
			// return gson.toJson(user);
			// return JsonUtil.getJsonStr(new RequestResult(true, "200" +" "+
			// userAccountDto));
		}
		response.setMessage("405");
		return response;
		// return gson.toJson("405");
		// return JsonUtil.getJsonStr(new RequestResult(true, "405"));
	}

	@RequestMapping(value = "/drawRandom.do")
	@ResponseBody
	public void drawRandom(HttpServletResponse reponse,
			HttpServletRequest request, HttpSession session) {
		RandomPicTools randomPicTools = new RandomPicTools();
		int width = 80;// 图片宽
		int height = 26;// 图片高
		int lineSize = 40;// 干扰线数量
		int stringNum = 4;// 随机产生字符数量
		session = request.getSession();
		// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
		g.setColor(randomPicTools.getRandColor(110, 133));
		// 绘制干扰线
		for (int i = 0; i <= lineSize; i++) {
			randomPicTools.drowLine(g, width, height);
		}
		// 绘制随机字符
		String randomString = "";
		for (int i = 1; i <= stringNum; i++) {
			randomString = randomPicTools.drowString(g, randomString, i);
		}
		session.removeAttribute("RandomCode");
		session.setAttribute("RandomCode", randomString);
		g.dispose();
		try {
			// 将内存中的图片通过流动形式输出到客户端
			ImageIO.write(image, "JPEG", reponse.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/login.do")
	@ResponseBody
	public Response<String> login(String accountName, String pwd,
			String randomPic, HttpSession session) {
		logger.debug("user login accountName=" + accountName + " pwd=" + pwd
				+ "randomPic=" + randomPic);
		Response<String> result = new Response<String>();
		if (accountName == "" || accountName == null || pwd == ""
				|| pwd == null) {
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
		LoginUserDto loginUserDto = new LoginUserDto();
		loginUserDto.setId(userAccountDto.getId());
		loginUserDto.setType(userAccountDto.getType());
		// session.removeAttribute(SessionConstants.LOGIN_USER_INFO);
		session.setAttribute(SessionConstants.LOGIN_USER_INFO, loginUserDto);

		System.out.println(session
				.getAttribute(SessionConstants.LOGIN_USER_INFO));

		// return JsonUtil.getJsonStr(new RequestResult(true, "200"));
		result.setData(userAccountDto.getUserAccount());
		result.setMessage(userAccountDto.getId());
		return result;
	}

	@RequestMapping(value = "/regUserByThird.do")
	@ResponseBody
	public Response<String> regUserByThird(
			@RequestParam("accountName") String accountName,
			HttpSession session, @RequestParam("type") int type,
			@RequestParam("thirdName") String thirdName) {
		logger.debug("user reguser third =" + accountName);
		Response<String> result = new Response<String>();
		if (accountName == null || "".equals(accountName)) {
			// return JsonUtil.getJsonStr(new RequestResult(true, "401"));
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("401");
			return result;
		}
		UserAccountDto userAccountDto = new UserAccountDto();
		userAccountDto.setType(UserTypeEnum.NORMALUSER.getIndex());
		userAccountDto.setUserAccount(UUIDGenerator.getUUID());
		userAccountDto.setPassword(UUIDGenerator.getUUID());
		userAccountDto.setThirdLogin(accountName);
		userAccountDto.setThirdType(type);

		String info = iUserService.thirdUserAccount(userAccountDto);

		// 插入users
		UsersDto usersDto = new UsersDto();
		usersDto.setUserAccount(info);
		usersDto.setNickName(thirdName);
		iUserService.insertOrUpdateUsersByThird(usersDto);

		LoginUserDto loginUserDto = new LoginUserDto();
		loginUserDto.setId(info);
		loginUserDto.setType(DataStatus.USERTYPE);
		session.removeAttribute(SessionConstants.LOGIN_USER_INFO);
		session.setAttribute(SessionConstants.LOGIN_USER_INFO, loginUserDto);
		// return JsonUtil.getJsonStr(new RequestResult(true, "200"));
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("200");
		result.setData(info);
		return result;
	}

	/**
	 * 用户退出操作
	 */
	@RequestMapping(value = "/loginout.do")
	public void loginout(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		session.removeAttribute(SessionConstants.LOGIN_USER_INFO);
		String redirectUrl = "/app/index.html";
		try {
			response.sendRedirect(redirectUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据session 获取用户信息
	 */
	@RequestMapping(value = "/loadInfo.do")
	@ResponseBody
	public Response<Object> loadInfo(HttpSession session) {
		Response<Object> result = new Response<Object>();
		// 获取当前登录用户Id
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		// 只允许登录用户进行操作
		if (loginUserDto == null) {
			// return JsonUtil.getJsonStr(new RequestResult(false,
			// "非登陆用户不允许查看基本信息!"));
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("非登陆用户不允许查看基本信息!");
			return result;
		}

//		UserAccountDto userAccountDto = iUserService.selectUserAccountByUserId(loginUserDto.getId());

		UsersDto usersDto = null;
		VenusInfoDto venusInfoDto = null;
		if (Objects.equal(loginUserDto.getType(), DataStatus.USERTYPE)) {// 普通用户
			usersDto = iUserService.selectUsersByUserAccountId(loginUserDto
					.getId());
			usersDto.setUserAccount(usersDto.getNickName());
//			if (usersDto == null) {
//				usersDto = new UsersDto();
//				usersDto.setUserAccount(userAccountDto.getUserAccount());
//			} else {
//				if (!StringUtils.isEmpty(usersDto.getNickName())) {
//					usersDto.setUserAccount(usersDto.getNickName());
//				} else {
//					usersDto.setUserAccount(userAccountDto.getUserAccount());
//				}
//			}
			result.setData(usersDto);
		} else if (Objects.equal(loginUserDto.getType(), DataStatus.SITETYPE)) {// 场馆用户
			venusInfoDto = iUserService
					.selectStadiumByUserAccountId(loginUserDto.getId());
			venusInfoDto.setUserAccount(venusInfoDto.getNickName());
//			if (venusInfoDto == null) {
//				venusInfoDto = new VenusInfoDto();
//				venusInfoDto.setUserAccount(userAccountDto.getUserAccount());
//			} else {
//				if (!StringUtils.isEmpty(venusInfoDto.getNickName())) {
//					venusInfoDto.setUserAccount(venusInfoDto.getNickName());
//				} else {
//					venusInfoDto
//							.setUserAccount(userAccountDto.getUserAccount());
//				}
//			}

			result.setData(venusInfoDto);
		} 
//		else if (Objects
//				.equal(loginUserDto.getType(), DataStatus.MANAGERTYPE)) {// 教练用户
//
//		}
		return result;
	}

	/**
	 * 
	 * 此方法描述的是：用户中心
	 * 
	 * @author: cwftalus@163.com
	 * @version: 2015年4月17日 下午2:04:36
	 */
	@RequestMapping(value = "/umanages.do")
	public void umanages(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		// 获取当前登录用户Id
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		// 只允许登录用户进行操作
		String redirectUrl = "/app/index.html";
		if (loginUserDto == null) {
			redirectUrl = "/app/index.html";
		} else {
			if (Objects.equal(loginUserDto.getType(), DataStatus.USERTYPE)) {// 普通用户
				redirectUrl = "/app/user/userIndex.html";
			} else if (Objects.equal(loginUserDto.getType(),
					DataStatus.SITETYPE)) {// 场馆用户
				redirectUrl = "/app/site/siteIndex.html";
			} 
//			else if (Objects.equal(loginUserDto.getType(),
//					DataStatus.MANAGERTYPE)) {// 教练用户
//
//			}
		}
		try {
			response.sendRedirect(redirectUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据session 获取用户信息
	 */
	@RequestMapping(value = "/loadEmail.do")
	@ResponseBody
	public Response<Object> loadEmail(HttpSession session) {
		Response<Object> result = new Response<Object>();
		// 获取当前登录用户Id
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		// 只允许登录用户进行操作
		if (loginUserDto == null) {
			// return JsonUtil.getJsonStr(new RequestResult(false,
			// "非登陆用户不允许查看基本信息!"));
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("非登陆用户不允许查看基本信息!");
			return result;
		}

		UsersDto usersDto = null;
		if (Objects.equal(loginUserDto.getType(), DataStatus.USERTYPE)) {// 普通用户
			usersDto = iUserService.selectUsersByUserAccountId(loginUserDto
					.getId());
			if (usersDto != null) {
				String email = usersDto.getEmail();
				if(!StringUtils.isEmpty(email)){
					String[] emails = email.split("@");
					result.setData("******@" + emails[1]);					
				}else{
					result.setData("******@163.com");
				}
			}
		}
		return result;
	}

}
