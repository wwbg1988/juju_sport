package com.juju.sport.home.controller.m.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.base.cache.ISportTypeCache;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.Response;
import com.juju.sport.order.service.IOrderService;
import com.juju.sport.user.dto.TeamListDto;
import com.juju.sport.user.dto.TeamListViewDto;
import com.juju.sport.user.service.IUserTeamListService;
import com.juju.sport.user.service.IUserTeamService;

/*
 * 
 * 此类描述的是：给移动端提供的查询所有战队
 * @author: Yin
 * @version: 2015年05月09日 下午12:41:52
 */
@Controller
@RequestMapping(value = "/api/m/team")
public class TeamMController {

	@Autowired
	private IUserTeamService iUserTeamService;

	@Autowired
	private IUserTeamListService iUserTeamListService;

	@Autowired
	private IOrderService iOrderService;
	
	@Autowired
	private ISportTypeCache iSportTypeCache;
	
	/**
	 * 
	 * 此方法描述的是：根据条件查询战队信息 无需登录
	 * 
	 * @author: JAM
	 * @version: 2015年05月09
	 */
	/*
	 * 查询战队列表
	 * @author: jam_Yin
	 * @version: 2015年5月5日 
	 */
	@RequestMapping(value = "/findTeams.do")
	@ResponseBody
	public Response<PageResult<TeamListViewDto>> findTeams(HttpSession session,TeamListDto teamListDto,PageQuery page){
		Response<PageResult<TeamListViewDto>> result = new Response<PageResult<TeamListViewDto>>();
		PageResult<TeamListViewDto> pageList = iUserTeamListService.findAllTeam(teamListDto,page);
		if (pageList != null) {
			List<TeamListViewDto> list =  pageList.getResults();
			for(TeamListViewDto l :list){
				l.setSportName(iSportTypeCache.findNameByID(l.getSportId()));
			}
			result.setData(pageList);
			result.setStatus(DataStatus.HTTP_SUCCESS);
			return result;
        }
		result.setStatus(DataStatus.HTTP_FAILE);
    	result.setMessage("查询失败！");
    	return result;			
	}
}