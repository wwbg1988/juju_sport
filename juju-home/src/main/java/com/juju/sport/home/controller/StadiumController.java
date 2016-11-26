package com.juju.sport.home.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.juju.sport.base.dto.MessagesDto;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.constants.SessionConstants;
import com.juju.sport.common.digest.MD5Coder;
import com.juju.sport.common.dto.Future;
import com.juju.sport.common.dto.WeatherDto;
import com.juju.sport.common.dto.WeatherMap;
import com.juju.sport.common.exception.SystemException;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.Response;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.common.util.HttpClientUtil;
import com.juju.sport.common.util.JsonUtil;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.common.util.WeatherUtil;
import com.juju.sport.home.util.SessionUtil;
import com.juju.sport.stadium.api.IStadiumService;
import com.juju.sport.stadium.dto.SpaceDto;
import com.juju.sport.stadium.service.ISpaceOpenTimeService;
import com.juju.sport.stadium.service.ISpaceService;
import com.juju.sport.user.api.IUserService;
import com.juju.sport.user.constants.UserTypeEnum;
import com.juju.sport.user.dto.LoginUserDto;
import com.juju.sport.user.dto.UserAccountDto;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.service.IUserVenuesInfoService;

@Controller
@RequestMapping(value = "/stadium")
public class StadiumController {
	protected static final Log logger = LogFactory
			.getLog(StadiumController.class);
	@Autowired
	private IUserService iUserService;

	public final static String SPACEMESS = "SPACEMESS";

	@Autowired
	private IStadiumService iStadiumService;

	@Autowired
	private ISpaceService iSpaceService;

	@Autowired
	private ISpaceOpenTimeService iSpaceOpenTimeService;
	
	@Autowired
	private IUserVenuesInfoService iUserVenuesInfoService;

	private static final String INDEXJUMP = "indexJump";

	@RequestMapping(value = "/registerSite.do")
	@ResponseBody
	// 注册场馆
	public Response<UserAccountDto> registerSite(HttpSession session,VenusInfoDto venusInfoDto,
			UserAccountDto userAccountDto,@RequestParam("venues_randomPic") String randomPic) {
		logger.debug("注册的用户为：" + userAccountDto.getUserAccount() + " 密码为："
				+ userAccountDto.getPassword());
		Response<UserAccountDto> result = new Response<UserAccountDto>();
		//验证码
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
				result.setMessage("验证码不存在");
				return result;
			}
		}
		
        if(iUserService.checkrepeat(userAccountDto.getUserAccount()) != 0){
        	result.setStatus(DataStatus.HTTP_SUCCESS);
        	result.setMessage("用户名已存在！");
        	return result;	
        }
        if(venusInfoDto.getVenueType()==null){
        	result.setMessage("场馆类型为空！");
        	return result;	
        }
		userAccountDto.setType(UserTypeEnum.SITEUSER.getIndex());
		String md5oldPwd;// 获取页面上输入的密码并加密校验
		try {
			md5oldPwd = MD5Coder.encodeMD5Hex(userAccountDto.getPassword());
			userAccountDto.setPassword(md5oldPwd);
		} catch (Exception e) {
			throw new SystemException(e.getMessage(), e);
		}
		venusInfoDto.setNickName(DataStatus._SITE_);
		venusInfoDto.setSpaceNum(0);
		UserAccountDto userAccount_ = iStadiumService.register(venusInfoDto, userAccountDto);
		if (userAccount_ == null) {
			//return JsonUtil.getJsonStr(new RequestResult(true, "对不起！注册失败！"));
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("对不起！注册失败！");					
			return result;
		} else {
			//return JsonUtil.getJsonStr(new RequestResult(true, "恭喜您注册成功！"));
			LoginUserDto loginUserDto = new LoginUserDto();
			loginUserDto.setId(userAccount_.getId());
			loginUserDto.setType(userAccount_.getType());
			session.setAttribute(SessionConstants.LOGIN_USER_INFO, loginUserDto);
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("恭喜您注册成功！");					
			return result;
		}
	}
	@RequestMapping(value = "/addOrUpdateStadiumInfo.do")
	@ResponseBody
	// 添加或修改场馆基础信息
	public Response<String> addOrUpdateStadiumInfo(HttpSession session,
			VenusInfoDto venusInfoDto,
			@RequestParam(value = "venueType",required = false) String stadiumType,
			@RequestParam(value = "otherServices",required = false) String otherService) {
		Response<String> result = new Response<String>();
		if (SessionUtil.getLoginSession(session) == null) {
			//return JsonUtil.getJsonStr(new RequestResult(true, "用户没有登陆或登陆失效！"));
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("用户没有登陆或登陆失效！");					
			return result;
		}
		if(venusInfoDto.getNickName()==null ||venusInfoDto.getNickName()==""){
        	result.setMessage("场馆名称为空！");
        	return result;	
        }
		if(venusInfoDto.getVenueType()==null){
        	result.setMessage("场馆类型为空！");
        	return result;	
        }
		String userAccountId = SessionUtil.getLoginSession(session).getId();
		if (!StringUtils.isEmpty(userAccountId)) {
			int flag = 0;
			List<VenusInfoDto> oldVenusInfoDtoList = iStadiumService
					.selectVenusInfoByUserAccountId(userAccountId);
			if (CollectionUtils.isNotEmpty(oldVenusInfoDtoList)) { 
				VenusInfoDto oldVenusInfoDto = oldVenusInfoDtoList.get(0);
				String venuesInfoId = oldVenusInfoDto.getId();
				//				List<VenuesSportType> sportTypeList =  iStadiumService.findSportType(venuesInfoId);  		//用户原来的运动类型
				//List<String>   venusInfoDto.getSportTypeList()
				iStadiumService.updateSportType(venuesInfoId);
				iStadiumService.updateServiceType(venuesInfoId);
				if(stadiumType != null){
					iStadiumService.insertSportType(stadiumType,venuesInfoId);}
				if(otherService != null){
					iStadiumService.insertServiceType(otherService,venuesInfoId);}

				venusInfoDto.setId(oldVenusInfoDto.getId());
				venusInfoDto.setUserAccountId(oldVenusInfoDto.getUserAccountId());
				flag = iStadiumService.updateVenusInfo(venusInfoDto);
			} else {
				venusInfoDto.setUserAccountId(userAccountId); // 新增
				venusInfoDto.setSpaceNum(0);
				flag = iStadiumService.insertVenusInfo(venusInfoDto,
						stadiumType, otherService);
			}
			if (flag > 0) {
				//return JsonUtil.getJsonStr(new RequestResult(true, "场馆信息保存成功"));
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setMessage("场馆信息保存成功!");					
				return result;
			}
		}
		//return JsonUtil.getJsonStr(new RequestResult(false, "场馆信息保存失败"));
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("场馆信息保存失败!");					
		return result;
	}

	@RequestMapping(value = "/showVenuesInfo.do")
	@ResponseBody
	// 回显场馆基本信息
	public Response<VenusInfoDto> showVenuesInfo(HttpSession session) {
		//Gson gson = new Gson();
		Response<VenusInfoDto> result = new Response<VenusInfoDto>();
		String userAccountId = null;
		if (SessionUtil.getLoginSession(session) == null) {
			//return JsonUtil.getJsonStr(new RequestResult(true, "用户没有登陆或登陆失效，请先登录！"));
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("用户没有登陆或登陆失效！");
			return result;
		}
		userAccountId = SessionUtil.getLoginSession(session).getId();
		VenusInfoDto venusInfoDto = iUserService.selectStadiumByUserAccountId(userAccountId);
		VenusInfoDto venusInfoDto_ = null;
		if(venusInfoDto!=null){

			venusInfoDto_ =  iStadiumService.selectVenusInfo(venusInfoDto);
		}

		if (venusInfoDto_ != null) {
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setData(venusInfoDto_);
			return result;
			//return gson.toJson(venusInfoDto);
			// return JsonUtil.getJsonStr(new RequestResult(true, "200" +" "+
			// userAccountDto));
		}
		//return gson.toJson("405");
		// return JsonUtil.getJsonStr(new RequestResult(true, "405"));
		result.setStatus(DataStatus.HTTP_FAILE);
		result.setMessage("405");
		return result;

	}

	@RequestMapping(value = "/findvenuesMessage.do")
	@ResponseBody
	// 查看场馆消息
	public Response<PageResult<MessagesDto>> findvenuesMessage(HttpSession session,PageQuery page) {
		Response<PageResult<MessagesDto>> result = new Response<PageResult<MessagesDto>>();
		LoginUserDto  loginUserDto = SessionUtil.getLoginSession(session);
		if(loginUserDto == null){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("用户还未登陆，请先登录！");//用户还未登陆，请先登录！
			return result;
		}
		MessagesDto messagesDto = new MessagesDto();
		messagesDto.setMsgToId(loginUserDto.getId());
		PageResult<MessagesDto> pageList = iStadiumService.findvenuesMessage(messagesDto,page);
		if (pageList != null) {
			result.setData(pageList);
			return result;
		}else{
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("数据查询失败");//查询失败
			return result;
		}
	}

	@RequestMapping(value = "/message.do")
	@ResponseBody
	// 查看用户对场馆的评价
	public Response<PageResult<MessagesDto>> findMessagebyUserName(HttpSession session,
			MessagesDto messagesDto, PageQuery page) {
		Response<PageResult<MessagesDto>> result = new Response<PageResult<MessagesDto>>();
		// 只允许登录用户进行操作
		/*
		 * if (session.getAttribute(SessionConstants.LOGIN_USER_INFO) == null ||
		 * "".equals(session.getAttribute(SessionConstants.LOGIN_USER_INFO))) {
		 * //return JsonUtil.getJsonStr(new RequestResult(true, "非登陆用户不允许查询!"));
		 * return "405"; }
		 */

		if (SessionUtil.getLoginSession(session) == null) {
			//return "405";
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("405");;
			return result;
		}
		PageResult<MessagesDto> pageList = iStadiumService
				.selectMessagebyUserName(messagesDto, page);
		// PageResult<MessagesDto> messageslist =
		// iStadiumService.selectMessagebyUserName(messagesDto,page);
		//Gson gson = new Gson();
		if (pageList != null) {
			//return gson.toJson(pageList);
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setData(pageList);
			return result;
		}
		//return gson.toJson("406");
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("406");;
		return result;
	}

	/**
	 * 
	 * 按照地区查询场馆
	 */
	@RequestMapping(value = "/findStadByAdd.do")
	@ResponseBody
	public Response<List<VenusInfoDto>> findStadByAdd(VenusInfoDto venusInfoDto) {
		//Gson gson = new Gson();
		Response<List<VenusInfoDto>> result = new Response<List<VenusInfoDto>>();
		List<VenusInfoDto> list = iUserService.findByAdd(venusInfoDto);
		//String jsonValue = gson.toJson(list);
		//return jsonValue;
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setData(list);
		return result; 
	}

	@RequestMapping(value="/jumpInfos.do")
	@ResponseBody
	public Response<String> jumpInfos(VenusInfoDto venusInfoDto,HttpSession session,HttpServletRequest request,HttpServletResponse response){
		Response<String> result = new Response<String>();

		session.removeAttribute(INDEXJUMP);
		session.setAttribute(INDEXJUMP, venusInfoDto);
		result.setMessage("200");
		return result;
	}
	@RequestMapping(value="/findNameBySportType.do")
	@ResponseBody
	public Response<List<VenusInfoDto>> findNameBySportType(VenusInfoDto venusInfoDto){
		Response<List<VenusInfoDto>> result = new Response<List<VenusInfoDto>>();
		List<VenusInfoDto> list = iUserService.findNameBySportType(venusInfoDto);
		result.setData(list);
		return result;
	}

	/**
	 * 找场馆页面查询场馆
	 */
	@RequestMapping(value = "/findChooseByAdd.do")
	@ResponseBody
	public Response<PageResult<VenusInfoDto>> findChooseStad(VenusInfoDto venusInfoDto, PageQuery page,HttpSession session) {

		Response<PageResult<VenusInfoDto>> result = new Response<PageResult<VenusInfoDto>>();
		if(session.getAttribute(INDEXJUMP)!=null){
			venusInfoDto =(VenusInfoDto)session.getAttribute(INDEXJUMP);
			page.setCurrPage(1);
			page.setPageSize(10);
			session.removeAttribute(INDEXJUMP);
		}
		//Gson gson = new Gson();
		
		//城市为上海市
		venusInfoDto.setProvinceid(310000);
		
		
		if (venusInfoDto.getNickName() != null
				&& !"".equals(venusInfoDto.getNickName())) {
			venusInfoDto.setCountryid(null);
			venusInfoDto.setOtherServices(null);
			venusInfoDto.setVenueType(null);
			venusInfoDto.setCityid(null);
			venusInfoDto.setId(null);
			venusInfoDto.setTeamType(null);
			venusInfoDto.setChargeType(null);
			String nickName = venusInfoDto.getNickName();
			venusInfoDto.setNickName("%"+nickName+"%");

			PageResult<VenusInfoDto> info = iUserService.findByServiceType(venusInfoDto, page);
			//return gson.toJson(info);
			
			//判断是否有场地
			/*List<VenusInfoDto> tempList = info.getResults();
			if(tempList!=null&&tempList.size()>0){
				for(VenusInfoDto tempVenus : tempList){
					SpaceDto param = new SpaceDto();
					param.setOwnerAccountId(tempVenus.getUserAccountId());
					List<SpaceDto> tempSpace = iSpaceService.findBy(param);
					if(tempSpace!=null&&tempSpace.size()>0){
						tempVenus.setIsFalse("0");
					}else{
						tempVenus.setIsFalse("1");
					}
				}
			}
			info.setResults(tempList);*/
			
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setData(info);
			return result; 
		}
		venusInfoDto.setNickName(null);

		if (venusInfoDto.getCountryid() == null
				|| venusInfoDto.getCountryid() == -1) {
			venusInfoDto.setCountryid(null);
		}
		if(venusInfoDto.getOtherServices()==null||"-1".equals(venusInfoDto.getOtherServices())||"".equals(venusInfoDto.getOtherServices())){
			venusInfoDto.setOtherServices(null);
		}
		if (venusInfoDto.getChargeType() == null
				||venusInfoDto.getChargeType() == -1) {
			venusInfoDto.setChargeType(null);
		}
		if(venusInfoDto.getTeamType()==null||"-1".equals(venusInfoDto.getTeamType())){
			venusInfoDto.setTeamType(null);
		}
		if(StringUtils.isEmpty(venusInfoDto.getId())||"-1".equals(venusInfoDto.getId())||"".equals(venusInfoDto.getId())){
			venusInfoDto.setId(null);
		}
		PageResult<VenusInfoDto> res = iUserService.findByServiceType(
				venusInfoDto, page);
		
		//判断是否有场地
		/*List<VenusInfoDto> tempList = res.getResults();
		if(tempList!=null&&tempList.size()>0){
			for(VenusInfoDto tempVenus : tempList){
				SpaceDto param = new SpaceDto();
				param.setOwnerAccountId(tempVenus.getUserAccountId());
				List<SpaceDto> tempSpace = iSpaceService.findBy(param);
				if(tempSpace!=null&&tempSpace.size()>0){
					tempVenus.setIsFalse("0");
				}else{
					tempVenus.setIsFalse("1");
				}
			}
		}
		res.setResults(tempList);*/
		
		//return gson.toJson(res);
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setData(res);
		return result; 
	}

	/**
	 * 精确查找场地
	 */
	@RequestMapping(value = "findStadByName.do")
	@ResponseBody
	public Response<String> findStadByName(HttpSession session, VenusInfoDto venusInfoDto) {
		Response<String> result = new Response<String>();
		String res = iUserService.findSearchByName(venusInfoDto);
		if (result != null && !"".equals(res)) {
			session.removeAttribute(SPACEMESS);
			session.setAttribute(SPACEMESS, res);
			//return "200";
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("200");;
			return result;
		} else {
			//return "404";
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("404");;
			return result;
		}
	}

	/***
	 * 此方法描述的是：选择场馆进入场地选择页面
	 * 
	 * @author: cwftalus@163.com
	 * @version: 2015年3月25日 下午5:22:15
	 * @stadium 当前场馆用户的Id
	 */
	@RequestMapping("{ownerAccountId}.do")
	public String content(HttpServletRequest request,
			@PathVariable String ownerAccountId,String chooseDate) throws Exception{
		//		// 获得模版路径执行跳转
		Future weathInfo = null;
		if(WeatherMap.weatherMap.isEmpty()){
			HashMap<String,Future> weathMap = WeatherUtil.loadWeatherApi();	
		}
		weathInfo = WeatherMap.weatherMap.get(DateUtils.format(new Date(), DateUtils.YMD));
		
		VenusInfoDto venusInfoDto = iUserVenuesInfoService.findById(ownerAccountId);
		request.setAttribute("nowDate", DateUtils.format(new Date(),DateUtils.YMD_DASH));
		request.setAttribute("sjWeek", DateUtils.dayForWeek(new Date()));
		request.setAttribute("ownerAccountId", ownerAccountId);
		request.setAttribute("venusInfoDto", venusInfoDto);
		request.setAttribute("weathInfo", weathInfo);
		return "/front/siteinfo";
	}
	
	/**
	 * 
		 * 此方法描述的是：根据场馆Id查询场馆的具体信息
		 * @author: cwftalus@163.com
		 * @version: 2015年4月30日 上午10:46:59
	 */
	@RequestMapping(value = "/showVenues.do")
	@ResponseBody	
	public Response<VenusInfoDto> showVenues(String vid){
		Response<VenusInfoDto> result = new Response<VenusInfoDto>();
		if(!StringUtils.isEmpty(vid)){
			VenusInfoDto data = iStadiumService.showVenues(vid);
			result.setData(data);			
		}else{
			result.setStatus(DataStatus.HTTP_FAILE);
		}
		return result;
	}
	
//	private HashMap<String,Future> loadWeatherApi(){
//		String wetherApi = PropertiesUtils.getProperty("weather.api.url");
//		String jsonData = HttpClientUtil.sendGetRequest(wetherApi, DataStatus.DECODECHARSET);
//		WeatherDto infoDto = JsonUtil.getObjFromJson(jsonData, WeatherDto.class);
//		for(Future future :infoDto.getResult().getFuture()){
//			WeatherMap.weatherMap.put(future.getDate(), future);
//		}
//		return WeatherMap.weatherMap;
//	}


	public static void main(String[] args) {
		String wetherApi = "http://v.juhe.cn/weather/index?format=2&cityname=上海&key=a77c5ec71c20c61eca3d734516dfdab2";//PropertiesUtils.getProperty("weather.api.url");
		String jsonData = HttpClientUtil.sendGetRequest(wetherApi, DataStatus.DECODECHARSET);
		Gson gson = new Gson();
		WeatherDto infoDto = JsonUtil.getObjFromJson(jsonData, WeatherDto.class);
		System.out.println("infoDto"+infoDto.getResult().getFuture().size());
	}


}
