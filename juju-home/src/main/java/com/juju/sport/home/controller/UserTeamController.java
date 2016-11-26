package com.juju.sport.home.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.base.cache.ISportTypeCache;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.constants.SessionConstants;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.Response;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.DateUtils;
import com.juju.sport.home.util.SessionUtil;
import com.juju.sport.order.dto.OrderItemsDto;
import com.juju.sport.order.service.IOrderItemsService;
import com.juju.sport.order.service.IOrderService;
import com.juju.sport.user.constants.TeamTypeEnum;
import com.juju.sport.user.dto.LoginUserDto;
import com.juju.sport.user.dto.TeamDto;
import com.juju.sport.user.dto.TeamListDto;
import com.juju.sport.user.dto.TeamListViewDto;
import com.juju.sport.user.service.IUserTeamListService;
import com.juju.sport.user.service.IUserTeamService;
/**
 * 此类描述的是：用户团队模块管理
 * @author: cwftalus@163.com
 * @version: 2015年4月2日 上午11:03:18
 */
@Controller
@RequestMapping(value = "/user/team")
public class UserTeamController {

	@Autowired
	private IUserTeamService iUserTeamService;

	@Autowired
	private IUserTeamListService iUserTeamListService;

	@Autowired
	private IOrderService iOrderService;

	@Autowired
	private IOrderItemsService iOrderItemsService;

	@Autowired
	private ISportTypeCache iSportTypeCache;

	/**
	 * 此方法描述的是：跳转到修改页面
	 * @author: cwftalus@163.com
	 * @version: 2015年4月9日 上午9:47:44
	 */
	@RequestMapping(value = "/modify.do")
	public String modify(HttpServletRequest request,String teamId){
		TeamDto teamDto = iUserTeamService.findByObj(teamId);
		request.setAttribute("teamDto", teamDto);
		return "/user/modifyTeam";
	}

	/**
	 * 此方法描述的是：创建战队
	 * @author: cwftalus@163.com
	 * @version: 2015年4月2日 上午11:02:53
	 */
	@RequestMapping(value = "/save.do")
	@ResponseBody
	public Response<String> save(HttpSession session, TeamDto teamDto) {
		Response<String> result = new Response<String>();
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		

		
		if(loginUserDto==null){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setData("非登陆用户不允许创建战队!");
		}else{
			//同一类型的战队最多创建一个
			String sportId = teamDto.getSportId();
			String userAccountId = loginUserDto.getId();
			List<TeamDto> teamDtoList = iUserTeamService.findSportId(userAccountId);
			if(teamDtoList != null && teamDtoList.size()>0){
				for(TeamDto teamDto_:teamDtoList){
					if(teamDto_.getSportId()!= null && teamDto_.getSportId().equals(sportId)){
						result.setStatus(DataStatus.HTTP_FAILE);
						result.setMessage("同一类型的战队最多只能创建一个!");
						return result;
					}
				}
			}
			
			teamDto.setUserAccountId(loginUserDto.getId());
			teamDto.setJoinNum(1);
			String teamId = iUserTeamService.saveOrUpdate(teamDto);	
			TeamListDto teamListDto = new TeamListDto();
			teamListDto.setTeamId(teamId);
			teamListDto.setUserAccountId(teamDto.getUserAccountId());
			teamListDto.setStatus(1);// 默认自己审核通過
			teamListDto.setTeamPosition(TeamTypeEnum.CAPTAIN.getIndex()+"");

			String teamListId = iUserTeamListService.saveOrUpdate(teamListDto);
			if(teamId !="0" && teamListId!="0"){
				result.setData("创建战队成功!");
			}else{
				result.setData("创建战队失败!");	
			}
		}
		return result;
		//		teamDto.setUserAccountId(loginUserDto.getId());
		//		String teamId = iUserTeamService.saveOrUpdate(teamDto);
		//		return JsonUtil.getJsonStr(new RequestResult(true, "创建战队成功!"));
	}

	/**
	 * 此方法描述的是：查询用户创建的战队信息
	 * @author: cwftalus@163.com
	 * @version: 2015年4月2日 上午11:48:28
	 */
	@RequestMapping(value = "/list.do")
	@ResponseBody
	public Response<List<TeamDto>> List(HttpSession session, TeamDto teamDto,
			PageQuery page) {
		Response<List<TeamDto>> result = new Response<List<TeamDto>>();
		List<TeamDto> list = new ArrayList<TeamDto>();
		//获取当前登录用户Id
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		// 只允许登录用户进行操作
		List<TeamDto> dataList = new ArrayList<TeamDto>();
		if (loginUserDto != null) {
			teamDto.setUserAccountId(loginUserDto.getId());
			list = iUserTeamService.findBy(teamDto);
			if(list.size() < 1){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("查询失败！");
				return result;
			}
			for(TeamDto tDto : list){
				TeamDto teamDt = BeanUtils.createBeanByTarget(tDto,TeamDto.class);
				teamDt.setSportName(iSportTypeCache.findNameByID(tDto.getSportId()));
				teamDt.setCreateTimeStr(DateUtils.format(teamDt.getCreateTime(),DateUtils.YMD_DASH));
				dataList.add(teamDt);
			}
			
		}
		result.setData(dataList);
		result.setStatus(DataStatus.HTTP_SUCCESS);
		return result;//JsonUtil.getJsonStr(new RequestResult(true, JsonUtil.getJsonStr(list)));
	}

	/**
	 * 此方法描述的是：查询用户创建的战队信息
	 * @author: JAM
	 * @version: 2015年4月2日 上午11:48:28

	@RequestMapping(value = "/myCreateList.do")
	@ResponseBody
	public Response<List<TeamDto>> List(HttpSession session, TeamDto teamDto,
			PageQuery page) {
		Response<List<TeamDto>> result = new Response<List<TeamDto>>();
		List<TeamDto> list = new ArrayList<TeamDto>();
		//获取当前登录用户Id
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		// 只允许登录用户进行操作
		List<TeamDto> dataList = new ArrayList<TeamDto>();
		if (loginUserDto != null) {
			teamDto.setUserAccountId(loginUserDto.getId());
			list = iUserTeamService.findBy(teamDto);
			for(TeamDto tDto : list){
				TeamDto teamDt = BeanUtils.createBeanByTarget(tDto,TeamDto.class);
				teamDt.setSportName(iSportTypeCache.findNameByID(tDto.getSportId()));
				teamDt.setCreateTimeStr(DateUtils.format(teamDt.getCreateTime(),DateUtils.YMD_DASH));
				dataList.add(teamDt);
			}
		}
		result.setData(dataList);
		return result;//JsonUtil.getJsonStr(new RequestResult(true, JsonUtil.getJsonStr(list)));
	}
	 */



	/**
	 * 此方法描述的是：查询本人加入的战队信息
	 * @author: cwftalus@163.com
	 * @version: 2015年4月2日 下午3:46:11
	 */
	@RequestMapping(value = "/joinlist.do")
	@ResponseBody
	public Response<List<TeamDto>> joinlist(HttpSession session, TeamListDto teamListDto,
			PageQuery page) {
		Response<List<TeamDto>> result = new Response<List<TeamDto>>();
		//获取当前登录用户Id
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		// 只允许登录用户进行操作
		if (loginUserDto != null) {
			List<TeamListDto> teamlist = new ArrayList<TeamListDto>();
			teamListDto.setUserAccountId(loginUserDto.getId());
			teamlist = iUserTeamListService.findBy(teamListDto);
			if(!teamlist.isEmpty()){
				List<String> teamIds = new ArrayList<String>();
				for(int i=0;i<teamlist.size();i++){
					TeamListDto teamObj = teamlist.get(i);
					teamIds.add(teamObj.getTeamId());
				}
				TeamDto teamDto = new TeamDto();
				teamDto.setTeamIds(teamIds);
				List<TeamDto> list = iUserTeamService.findBy(teamDto);
				
				if(list != null && list.size()>0){
					for(TeamDto tDto : list){
						tDto.setSportName(iSportTypeCache.findNameByID(tDto.getSportId()));
					}
					result.setStatus(DataStatus.HTTP_SUCCESS);
					result.setData(list);
				}else{
					result.setMessage("用户还没有加入战队！");
					result.setStatus(DataStatus.HTTP_FAILE);	
				}
			/*	String userAccountId = loginUserDto.getId();
				List<TeamDto> list = iUserTeamService.findJoinList(userAccountId);
				if(list != null && list.size()>0){
					for(TeamDto tDto : list){
						tDto.setSportName(iSportTypeCache.findNameByID(tDto.getSportId()));
					}
					result.setStatus(DataStatus.HTTP_SUCCESS);
					result.setData(list);
				}else{
					result.setMessage("用户还没有加入战队！");
					result.setStatus(DataStatus.HTTP_FAILE);	
				}*/
				
			}
		}
		return result;//JsonUtil.getJsonStr(new RequestResult(true, JsonUtil.getJsonStr(result)));
	}

	/**
	 * 此方法描述的是：查询战队成员列表信息
	 * @author: jam_Yin
	 * @version: 2015年4月30日 下午3:03:19
	 */
	@RequestMapping(value = "/showMembersInfo.do")
	@ResponseBody
	public Response<List<TeamListViewDto>> showMembersInfo(HttpSession session, TeamDto teamDto,
			PageQuery page) {
		Response<List<TeamListViewDto>> result = new Response<List<TeamListViewDto>>();
		//获取当前登录用户Id
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		// 只允许登录用户进行操作
		if (loginUserDto != null) {
			if(teamDto != null){
				List<TeamListViewDto>	list = iUserTeamListService.showTeamMembers(teamDto.getId());
				/*if(list != null && list.size()>0 ){
					for(TeamListViewDto teamList:list){
						teamList.setSportName(iSportTypeCache.findNameByID(teamList.getSportId()));
					}
				}*/
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setData(list);
			}
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("查询失败！");
		}
		return result;
	}


	/**
	 * 此方法描述的是：解散自己創建的戰隊
	 * @author: cwftalus@163.com
	 * @version: 2015年4月2日 下午3:02:03
	 */
	@RequestMapping(value = "/cancel.do")
	@ResponseBody
	public Response<String> cancel(HttpSession session, TeamDto teamDto) {
		Response<String> result = new Response<String>(); 
		// 只允许登录用户进行操作
		LoginUserDto loginUserDto = null;
		if(session.getAttribute(SessionConstants.LOGIN_USER_INFO)==null){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setData("非登陆用户不允许解散操作");
			return result; 
			//return JsonUtil.getJsonStr(new RequestResult(true, "非登陆用户不允许解散操作!"));
		}
		loginUserDto = (LoginUserDto) session.getAttribute(SessionConstants.LOGIN_USER_INFO);
		String userId =loginUserDto.getId();
		teamDto.setUserAccountId(userId);
		List<TeamDto> resultList = iUserTeamService.findBy(teamDto);
		if (resultList != null && resultList.size() > 0) {
			teamDto = resultList.get(0);
			teamDto.setStat(DataStatus.DISABLED);
			iUserTeamService.saveOrUpdate(teamDto);
		}
		result.setStatus(DataStatus.HTTP_FAILE);
		result.setData("解散战队成功!");
		return result;
		//return JsonUtil.getJsonStr(new RequestResult(true, "解散战队成功!"));
	}

	/**
	 * 此方法描述的是：根据条件查询战队信息 无需登录
	 * @author: cwftalus@163.com
	 * @version: 2015年4月2日 下午3:46:11
	 */
	@RequestMapping(value = "/findBy.do")
	@ResponseBody
	public Response<List<TeamDto>> findBy(TeamDto teamDto,PageQuery page) {
		/*		Response<List<TeamDto>> result =  new Response<List<TeamDto>>();
		//List<TeamDto> list = new ArrayList<TeamDto>();
		Response<List<TeamDto>> list = iUserTeamService.findBy(teamDto,page);
		if(list != null && list.size() > 0){
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setData(list);
		return result;
		}else{
		result.setStatus(DataStatus.HTTP_FAILE);
		result.setData(list);
		return result;	
		}*/
		return null;
	}

	/**
	 * 此方法描述的是：根据战队类型或战队名查询战队信息
	 * @author: jam_Yin
	 * @version: 2015年4月29日 下午3:59:19

	@RequestMapping(value = "/findTeam.do")
	@ResponseBody
	public Response<PageResult<TeamDto>> findTeam(TeamDto teamDto,PageQuery page) {
		Response<PageResult<TeamDto>> results = new Response<PageResult<TeamDto>>();
		ArrayList<TeamListDto> teamListDto = new ArrayList<TeamListDto>();
		teamListDto = iUserTeamService.findTeam(teamDto, page);
		results.setData(teamListDto);
		return results;
	}
	 */

	/**
	 * 查询可以加入的战队列表
	 * @author: jam_Yin
	 * @version: 2015年5月5日 
	 */
	@RequestMapping(value = "/findTeams.do")
	@ResponseBody
	public Response<PageResult<TeamListViewDto>> findTeams(HttpSession session,TeamListDto teamListDto,PageQuery page){
		Response<PageResult<TeamListViewDto>> result = new Response<PageResult<TeamListViewDto>>();
		if(SessionUtil.getLoginSession(session) == null){
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("用户未登录，请先登录！");
			return result;
		}
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		String userAccountId = loginUserDto.getId();
		teamListDto.setUserAccountId(userAccountId);
		PageResult<TeamListViewDto> pageList = iUserTeamListService.findJoinTeam(teamListDto,page);

		if (pageList != null) {
			List<TeamListViewDto>  list = pageList.getResults();
			for(TeamListViewDto teamList: list){
				teamList.setSportName(iSportTypeCache.findNameByID(teamList.getSportId()));
			}
			result.setData(pageList);
			result.setStatus(DataStatus.HTTP_SUCCESS);
			return result;
		}
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("没有可以加入的战队！");
		return result;			
	}

	/**
	 * 加入战队
	 * @author: jam_Yin
	 * @version: 2015年5月5日 
	 */
	@RequestMapping(value = "/join.do")
	@ResponseBody	
	public Response<String> join(HttpSession session,TeamListDto teamListDto,@RequestParam(value = "sportId",required = false) String sportId){
		Response<String> result = new Response<String>(); 
		// 只允许登录用户进行操作
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		//		SessionUtil.getLoginSession(session);
		if(loginUserDto==null){
			//return JsonUtil.getJsonStr(new RequestResult(false, "非登陆用户不允许申请加入战队!"));
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("非登陆用户不允许申请加入战队!");
			return result;
		}
		//判断该用户是否已经加入过该战队
		String userAccountId = loginUserDto.getId();
		TeamListDto teamListDto_ = new TeamListDto();
		teamListDto_.setUserAccountId(userAccountId);
		List<TeamListDto> teamList = iUserTeamListService.findBy(teamListDto_);
		for(TeamListDto team:teamList){
			if(team.getTeamId() != null && team.getTeamId().equals(teamListDto.getTeamId())){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("同一战队不能加入多次!");
				return result;
			}
		}

		//同一类型的战队最多加一个
		List<TeamListViewDto> teamSportList = iUserTeamListService.findTeamsAndSportId(userAccountId,sportId);
		if(teamSportList != null && teamSportList.size()>0){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("同一类型的战队最多只能加一个!");
			return result;
		}

		//不允许加入自己的战队
		TeamDto teamDto = new TeamDto();
		teamDto.setId(teamListDto.getTeamId());
		//		teamDto.setUserAccountId(userAccountId);
		List<TeamDto> teams = iUserTeamService.findBy(teamDto);
		if(teams.isEmpty()){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("该战队不存在!");
			return result;
		}

		TeamDto teamObj = teams.get(0);
		if(teamObj.getUserAccountId().equals(loginUserDto.getId())){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("不能加入自己的战队!");
			return result;				
		}
		
		//战队最大人数为空
		if(teamObj.getMaxNum()==null){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("战队最大人数为空!");
				return result;
			}
		//加入人数不能超过最大人数
		if(teamObj.getJoinNum() < teamObj.getMaxNum()){
		teamObj.setJoinNum(teamObj.getJoinNum()+1);
		}else{
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("人数超过最多人数!");
			return result;
		}
		iUserTeamService.saveOrUpdate(teamObj);		

		//		for(TeamDto team: teams){
		//			if(team.getId() !=null && team.getId().equals(teamListDto.getTeamId())){
		//				result.setStatus(DataStatus.HTTP_FAILE);
		//				result.setMessage("不能加入自己的战队!");
		//				return result;
		//			}
		//		}




		/*if(!teams.isEmpty()){
			//return JsonUtil.getJsonStr(new RequestResult(false, "不能加入自己的战队!"));
			result.setStatus(DataStatus.HTTP_SUCCESS);
	    	result.setMessage("不能加入自己的战队!");
	    	return result;
		}*/

		teamListDto.setStatus(1);				//默认审核通过
		teamListDto.setTeamPosition(TeamTypeEnum.NORMAL.getIndex()+"");			//默认为普通队员（队长为1）
		teamListDto.setUserAccountId(userAccountId);
		String teamListId = iUserTeamListService.saveOrUpdate(teamListDto);
		//return JsonUtil.getJsonStr(new RequestResult(true, "申请加入战队成功!"));

		/*
		 * 加入成功之后自动加1
		 */
		//		if(!teams.isEmpty()){
		//			TeamDto teamObj = teams.get(0);
		//			teamObj.setJoinNum(teamObj.getJoinNum()+1);
		//			iUserTeamService.saveOrUpdate(teamObj);
		//		}

		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("申请加入战队成功!");
		return result;
	}


	/** 
	 * 此方法描述的是：发布对战显示预订过的场地地点和预订时间
	 * @author: jam_yin
	 * @version: 2015年4月10日 下午15:38:28
	 */
	@RequestMapping(value = "/showInfo.do")
	@ResponseBody	
	public Response<List<OrderItemsDto>> showInfo(HttpSession session){
		Response<List<OrderItemsDto>> result =  new Response<List<OrderItemsDto>>();
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		if(loginUserDto==null){
			//return JsonUtil.getJsonStr(new RequestResult(false, "用户没有登录，请先登录!"));
			//return JsonUtil.getJsonStr(new RequestResult(true, "405"));
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("用户没有登录，请先登录!");
			return result;
		}
		String userAccountId = loginUserDto.getId();
		List<OrderItemsDto> orderItemList = new ArrayList<OrderItemsDto>();
		if(null != userAccountId){
			orderItemList = iOrderItemsService.findOrderByUserAccountId(userAccountId);}
		//Gson gson = new Gson();
		if (orderItemList != null) {
			//return gson.toJson(orderList);
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setData(orderItemList);
			return result;
		}
		//return gson.toJson("406");
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("查询失败!");
		return result;
	}

	/** 
	 * 此方法描述的是：发布对战
	 * @author: jam_yin
	 * @version: 2015年4月12日 下午12:30:25
	 */
	@RequestMapping(value = "/releaseFright.do")
	@ResponseBody	
	public Response<String> releaseFright(HttpSession session,TeamDto teamDto){
		Response<String> result = new Response<String>();
		// 只允许登录发布对战
		LoginUserDto loginUserDto = SessionUtil.getLoginSession(session);
		if(loginUserDto==null){
			//return JsonUtil.getJsonStr(new RequestResult(false, "405"));
			//return "405";
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("用户没有登录，请先登录!");
			return result;
		}
		int res = iUserTeamService.releaseFright(teamDto);
		if(res == 0){
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("406");
			return result;
			//return JsonUtil.getJsonStr(new RequestResult(true, "406"));
			//return "406";
		}else{
			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("200");
			return result;
			//return JsonUtil.getJsonStr(new RequestResult(true, "200"));
			//return "200";
		}

	}

}
