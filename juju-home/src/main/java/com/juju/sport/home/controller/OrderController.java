package com.juju.sport.home.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.constants.SessionConstants;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.Response;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.common.util.UUIDGenerator;
import com.juju.sport.home.util.SessionUtil;
import com.juju.sport.order.constants.OrderStatus;
import com.juju.sport.order.dto.OrderDto;
import com.juju.sport.order.dto.OrderItemsDto;
import com.juju.sport.order.dto.OrderUserDto;
import com.juju.sport.order.pojo.OrderItems;
import com.juju.sport.order.service.IOrderItemsService;
import com.juju.sport.order.service.IOrderMappService;
import com.juju.sport.order.service.IOrderService;
import com.juju.sport.stadium.dto.SpaceDto;
import com.juju.sport.stadium.service.ISpaceService;
import com.juju.sport.user.api.IUserService;
import com.juju.sport.user.constants.TeamTypeEnum;
import com.juju.sport.user.dto.LoginUserDto;
import com.juju.sport.user.dto.TeamListDto;
import com.juju.sport.user.dto.UserAccountDto;
import com.juju.sport.user.dto.UsersDto;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.service.ITeamManagerService;
import com.juju.sport.user.service.IUserTeamListService;
import com.juju.sport.user.service.IUserVenuesInfoService;

/**
 * 
 * 此类描述的是：订单管理模块controller
 * 
 * @author: cwftalus@163.com
 * @version: 2015年3月25日 上午10:13:34
 */

@Controller
@RequestMapping(value = "/order")
public class OrderController {

	/**
	 * 
	 * 此方法描述的是：订单查询方法
	 * 
	 * @author: cwftalus@163.com
	 * @version: 2015年3月25日 上午10:16:18
	 */

	@Autowired
	private IOrderItemsService iOrderItemsService;
	
	
	@Autowired
	private IUserVenuesInfoService iUserVenuesInfoService;
	
	
	@Autowired
	private IOrderService iOrderService;
	

	@Autowired
	private IUserService iUserService;
	
	@Autowired
	private IOrderMappService iOrderMappService;
	
	@Autowired
	private ISpaceService iSpaceService;
	
	@Autowired
	private IUserTeamListService iUserTeamListService;
	
	
	
	private final static int GUOQITIME=3600;
	
	
	

	@RequestMapping(value = "/items/list.do")
	@ResponseBody
	public Response<PageResult<OrderItemsDto>> List(HttpSession session,OrderItemsDto orderItemsDto,PageQuery page) {
		Response<PageResult<OrderItemsDto>> result = new Response<PageResult<OrderItemsDto>>();
		String userId="";
		LoginUserDto loginUserDto = null;
		if(session.getAttribute(SessionConstants.LOGIN_USER_INFO)!=null){
			loginUserDto = (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
			if(loginUserDto.getType()!=2){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("401");
				return result;
			}
			userId=loginUserDto.getId();
		}else{
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("404");
			return result;
		}
		orderItemsDto.setOwnerAccountId(userId);
		orderItemsDto.setStat(1);
//		int count = iOrderItemsService.findOrderCount(orderItemsDto);
//		page.setTotal(count);
		PageResult<OrderItemsDto> pageList = iOrderItemsService.findBy(
				orderItemsDto, page);
		
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setData(pageList);
		return result;
	}
	
	@RequestMapping(value="/items/search.do")
	@ResponseBody
	public  Response<PageResult<OrderItemsDto>> searchList(HttpSession session,OrderItemsDto orderItemsDto,PageQuery page){
		Response<PageResult<OrderItemsDto>> result = new Response<PageResult<OrderItemsDto>>();
		String userId="";
		LoginUserDto loginUserDto = null;
		if(session.getAttribute(SessionConstants.LOGIN_USER_INFO)!=null){
			loginUserDto = (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
			if(loginUserDto.getType()!=2){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("401");
				return result;
			}
			userId=loginUserDto.getId();
		}else{
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("401");
			return result;
		}
		orderItemsDto.setOwnerAccountId(userId);
		orderItemsDto.setStat(1);
//		int count = iOrderItemsService.findOrderCount(orderItemsDto);
//		page.setTotal(count);
		if(!StringUtils.isEmpty(orderItemsDto.getOrderTime())){
			orderItemsDto.setSearchStartDate(DateUtils.parse(orderItemsDto.getOrderTime(), DateUtils.YMD_DASH));
		}
		if(!StringUtils.isEmpty(orderItemsDto.getEndTime())){
			orderItemsDto.setSearchEndDate(DateUtils.parse(orderItemsDto.getEndTime(), DateUtils.YMD_DASH));
		}
		PageResult<OrderItemsDto> pageList = iOrderItemsService.findBy(
				orderItemsDto, page);

		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setData(pageList);
		return result;
	}
	/**
	 * 
		 * 此方法描述的是：获取用户购买的最近的几条记录
		 * @author: cwftalus@163.com
		 * @version: 2015年4月3日 上午9:31:15
	 */
	@RequestMapping(value = "/userList.do")
	@ResponseBody
	public Response<Object> userList(HttpSession session,OrderItemsDto orderItemsDto,PageQuery page) {
		Response<Object> result = new Response<Object>();
		//获取当前登录用户Id
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		// 只允许登录用户进行操作
		if (loginUserDto == null) {
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("非登陆用户不允许下订单!");
			return result;
//			return JsonUtil.getJsonStr(new RequestResult(false, "非登陆用户不允许下订单!"));
		}
		
		orderItemsDto.setUserAccountId(loginUserDto.getId());
		orderItemsDto.setStat(DataStatus.ENABLED);
		PageResult<OrderItemsDto> pageList = iOrderItemsService.findBy(
				orderItemsDto, page);
		List<String> userIds = new ArrayList<String>();
		Iterator<OrderItemsDto> its = pageList.getResults().iterator();
		while(its.hasNext()){
			OrderItemsDto orItemsDto = its.next();
			userIds.add(orItemsDto.getOwnerAccountId());
		}
		HashMap<String, VenusInfoDto> infoMap = iUserVenuesInfoService.findByToMap(userIds);
		
		PageResult<OrderItemsDto> pageResultList = new PageResult<OrderItemsDto>();
		pageResultList.setCurrPage(pageList.getCurrPage());
		pageResultList.setTotal(pageList.getTotal());
		pageResultList.setPageSize(pageList.getPageSize());
		
		List<OrderItemsDto> results = new ArrayList<OrderItemsDto>();
		Iterator<OrderItemsDto> itss = pageList.getResults().iterator();
		while(itss.hasNext()){
			OrderItemsDto orItemsDto = itss.next();
			VenusInfoDto infoDto  = infoMap.get(orItemsDto.getOwnerAccountId());
			if(infoDto!=null){
				orItemsDto.setAddress(infoDto.getAddress());	
			}else{
				orItemsDto.setAddress("");
			}
			orItemsDto.setStatusName(OrderStatus.getStatusDesc(orItemsDto.getOrderStatus()));
			results.add(orItemsDto);
		}
		pageResultList.setResults(results);
		result.setData(pageResultList);
		return result;
//		return JsonUtil.getJsonStr(pageResultList);
	}
	
	/**
	 * 
		 * 此方法描述的是：用户针对订单操作的动作
		 * @author: cwftalus@163.com
		 * @version: 2015年5月2日 下午1:59:47
	 */
	@RequestMapping(value = "/operation.do")
	@ResponseBody
	public Response<String> operation(HttpSession session,OrderItemsDto orderItemsDto,PageQuery page) {
		Response<String> result = new Response<String>();
		
		return result;
	}
	/**
	 * 此方法描述是：我要报名
	 */
	@RequestMapping(value="/registration.do")
	@ResponseBody
	public Response<String> registration(@RequestParam("orderItems")String orderItems,@RequestParam("teamListId")String teamListId,HttpSession session){
		Response<String> result = new Response<String>();
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		if(loginUserDto!=null){
			if(loginUserDto.getType()!=1){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("登录用户不是普通用户");
				return result;
			}
		}else{
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("用户未登录");
			result.setData("404");
			return result;
		}
		OrderItemsDto orderItemsDto = iOrderItemsService.findByItemId(orderItems);
		if(orderItemsDto!=null){
			if(orderItemsDto.getOrderStatus()!=3){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("报名已结束");
				return result;
			}
			
			OrderItemsDto tempDto = new OrderItemsDto();
			tempDto.setSpaceId(orderItemsDto.getSpaceId());
			tempDto.setDate(orderItemsDto.getDate());
			tempDto.setOrderTime(orderItemsDto.getOrderTime());
			tempDto.setEndTime(orderItemsDto.getEndTime());
			tempDto.setOrderStatus(OrderStatus.Finished.getValue());
			int tempInt = iOrderItemsService.findCountItems(tempDto);
			if(tempInt>0){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("场地已被预订，无法报名");
				result.setData("409");
				return result;
			}
			
			OrderDto orderDto = iOrderService.findOne(orderItemsDto.getOrderId());
			long diffTime = DateUtils.diffNowSecond(orderDto.getCreateTime());
			if(diffTime>=GUOQITIME){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("报名时间已过期");
				result.setData("402");
				return result;
			}
		}else{
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("查询订单详细信息失败!");
			return result;
		}
		
		
		OrderUserDto orderUserDto = new OrderUserDto();
		orderUserDto.setOrderId(orderItemsDto.getId());
		int orderUserCount = iOrderMappService.findCountOrder(orderUserDto);
		
		SpaceDto spaceDto = new SpaceDto();
		spaceDto.setId(orderItemsDto.getSpaceId());
		List<SpaceDto> spaceList =iSpaceService.findBy(spaceDto);
		int minNum = 0;
		if(spaceList!=null&&spaceList.size()>0){
			SpaceDto tempSpace =spaceList.get(0);
			if(tempSpace.getMinNumber()!=null){
				minNum = tempSpace.getMinNumber();
			}
		}
		
		int counts2 = minNum-orderUserCount;
		int teamCount = 0;
		if(teamListId!=null&&!"-1".equals(teamListId)){
			TeamListDto paramss = new TeamListDto();
			paramss.setTeamId(teamListId);
			List<TeamListDto> tempTeamList = iUserTeamListService.findBy(paramss);
			if(tempTeamList!=null&&tempTeamList.size()>0){
				teamCount = tempTeamList.size();
				counts2=minNum-teamCount;
			}else{
				counts2 = minNum;
			}
		}
	
		
		OrderUserDto usersDto = new OrderUserDto();
		usersDto.setOrderId(orderItemsDto.getId());
		usersDto.setUserAccountId(loginUserDto.getId());
		int userCount = iOrderMappService.findCountOrder(usersDto);
		if(userCount>0){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("您已报名，不可重复提交");
			return result;
		}
		
		
		OrderUserDto param = new OrderUserDto();
		param.setOrderId(orderItemsDto.getId());
		param.setUserAccountId(loginUserDto.getId());
		param.setStat(DataStatus.ENABLED);
		param.setCreateTime(new Date());
		param.setLastUpdateTime(new Date());
		param.setId(UUIDGenerator.getUUID());
		
		iOrderMappService.insertOrder(param);
		
		
		if(minNum!=0&&counts2>0){
		}else{
			//如果说场地预订人数大于最低限定人数，修改订单状态为结束
			orderItemsDto.setOrderStatus(OrderStatus.Finished.getValue());
			iOrderItemsService.updateItemsInfo(orderItemsDto);
			
//			result.setStatus(DataStatus.HTTP_FAILE);
//			result.setMessage("人数超过场地最低限定人数");
//			return result;
		}
		
		if(minNum<=orderUserCount+1){
			orderItemsDto.setOrderStatus(OrderStatus.Finished.getValue());
			iOrderItemsService.updateItemsInfo(orderItemsDto);
		}
		
		
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("报名成功");
		return result;
		
		
		
	}
	
	/**
	 * 此方法描述的是加入比赛的一些基本信息
	 */
	@RequestMapping(value="/showRaceInfos.do")
	@ResponseBody
	public Response<List<Map<String,String>>> showRaceInfos(@RequestParam("orderId")String orderId){
		Response<List<Map<String,String>>> result = new Response<List<Map<String,String>>>();
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		String addresses = "";
		String nickName = "";
		String userNickName = "";
		String orderTime="";
		String teamListId="";
		if(!StringUtils.isEmpty(orderId)&&!"".equals(orderId)){
			OrderDto orderDto = iOrderService.findOne(orderId);
			
			//加入时间60分钟
			
			long diffTime = DateUtils.diffNowSecond(orderDto.getCreateTime());
			if(diffTime>=GUOQITIME){
				diffTime = 0;
			}else{
				diffTime = GUOQITIME-diffTime;
			}
			
			String venusId = orderDto.getOwnerAccountId();
			if(!StringUtils.isEmpty(venusId)&&!"".equals(venusId)){
				VenusInfoDto venusInfoDto = iUserVenuesInfoService.findById(venusId);
				if(venusInfoDto!=null){
//					map.put("address", venusInfoDto.getAddress());
//					map.put("nickName", venusInfoDto.getNickName());
					addresses = venusInfoDto.getAddress();
					nickName = venusInfoDto.getNickName();
				}
			}
			String userId = orderDto.getUserAccountId();
			if(!StringUtils.isEmpty(userId)&&!"".equals(userId)){
				UsersDto userDto = iUserService.selectUsersByUserAccountId(userId);
				if(userDto!=null){
//					map.put("userNickName", userDto.getNickName());
					userNickName = userDto.getNickName();
					if(userNickName==null||"".equals(userNickName)){
						UserAccountDto userAccountDto = iUserService.selectUserAccountByUserId(userId);
						if(userAccountDto!=null){
							userNickName=userAccountDto.getUserAccount();
						}
					}
				}else{
					UserAccountDto userAccountDto = iUserService.selectUserAccountByUserId(userId);
					if(userAccountDto!=null){
						userNickName=userAccountDto.getUserAccount();
					}
				}
			}
			
			//如果是战队创建订单 寻找队长所在的战队ID
			TeamListDto teamListDto = new TeamListDto();
			teamListDto.setTeamPosition(TeamTypeEnum.CAPTAIN.getIndex()+"");
			teamListDto.setUserAccountId(userId);
			List<TeamListDto> tempTeamList = iUserTeamListService.findFootClub(teamListDto);
			if(tempTeamList!=null&&tempTeamList.size()>0){
				teamListId = tempTeamList.get(0).getTeamId();
			}
			
			
			OrderItemsDto orderItemsDto = new OrderItemsDto();
			orderItemsDto.setOrderId(orderId);
			List<OrderItemsDto> itemList = iOrderItemsService.findListBy(orderItemsDto);
			if(itemList!=null&&itemList.size()>0){
				for(OrderItemsDto tempOrderItems :itemList){
					orderTime=tempOrderItems.getDate()+" "+tempOrderItems.getOrderTime()+":00-"+tempOrderItems.getEndTime()+":00";
					OrderUserDto orderUserDto = new OrderUserDto();
					orderUserDto.setOrderId(tempOrderItems.getId());
					int counts = iOrderMappService.findCountOrder(orderUserDto);
					SpaceDto spaceDto = new SpaceDto();
					spaceDto.setId(tempOrderItems.getSpaceId());
					List<SpaceDto> spaceList =iSpaceService.findBy(spaceDto);
					
					int minNum = 0;
					boolean minFlag = false;
					if(spaceList!=null&&spaceList.size()>0){
						SpaceDto tempSpace =spaceList.get(0);
						if(tempSpace.getMinNumber()!=null){
							minNum = tempSpace.getMinNumber();
						}
					}
					int counts2 = minNum-counts;
					if(minNum!=0&&counts2>0){
						minFlag = true;
					}
					
					
					Map<String,String> map = new HashMap<String,String>();
					map.put("address", addresses);
					map.put("nickName", nickName);
					map.put("orderTime", orderTime);
					map.put("userNickName", userNickName);
					map.put("itemId", tempOrderItems.getId());
					map.put("teamListId", teamListId);
					long min = diffTime/60;
					long second =diffTime%60;
					map.put("diffTime",String.valueOf(min));
					map.put("diffSecond", String.valueOf(second));
					map.put("orderStat", String.valueOf(tempOrderItems.getOrderStatus()));
					map.put("countsFlag", String.valueOf(minFlag));
					resultList.add(map);
				}
				
			}
		}
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setData(resultList);
		return result;
	}
}
