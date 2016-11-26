package com.juju.sport.home.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.common.model.Response;
import com.juju.sport.game.dto.RaceInfoDto;
import com.juju.sport.game.dto.RaceListDto;
import com.juju.sport.game.dto.RaceScoreboardDto;
import com.juju.sport.game.service.IRaceScoreboardService;
import com.juju.sport.game.service.IRaceService;

@Controller
@RequestMapping(value = "/race")
public class RaceController {

	@Autowired
	private IRaceService iRaceService;

	@Autowired
	private IRaceScoreboardService iRaceScoreboardService;
	
	private static final String RACEDETAILS = "raceDetails";

	@RequestMapping("/findAll.do")
	@ResponseBody
	public Response<List<RaceInfoDto>> findAll(HttpSession session) {
		Response<List<RaceInfoDto>> result = new Response<List<RaceInfoDto>>();
		List<RaceInfoDto> list = iRaceService.findAll();
		result.setData(list);
		return result;
	}

	@RequestMapping("findDetail.do")
	@ResponseBody
	public Response<RaceListDto> findDetail(HttpSession session,
			HttpServletRequest request) {
		Response<RaceListDto> result = new Response<RaceListDto>();
		RaceInfoDto raceInfoDto = new RaceInfoDto();
		raceInfoDto.setId(session.getAttribute(RACEDETAILS).toString());
		RaceListDto raceListDto = iRaceService.findDetails(raceInfoDto);
		result.setData(raceListDto);
		return result;

	}

//	@RequestMapping(value = "/jumpInfos.do")
//	public void jumpInfos(RaceInfoDto raceInfoDto, HttpSession session,
//			HttpServletRequest request, HttpServletResponse response) {
//
//		session.removeAttribute(RACEDETAILS);
//		session.setAttribute(RACEDETAILS, raceInfoDto.getId());
//
//		try {
//			response.sendRedirect("/app/raceDetail.html");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	/**
	 * 此方法描述的是：聚聚赛事 跳转到详情页面
	 * @author: cwftalus@163.com
	 * @version: 2015年4月17日 上午10:05:36
	 */
	@RequestMapping(value = "/poly_event.do")
	public String poly_event(HttpServletRequest request,String raceId) {
		request.setAttribute("raceId", raceId);
		return "/front/poly_event";
	}
	
	/**
	 * 
		 * 此方法描述的是：
		 * @author: cwftalus@163.com
		 * @version: 2015年4月17日 上午11:41:22
	 */
	@RequestMapping(value = "/event_info.do")
	public String event_info(HttpServletRequest request,String infoId) {
		request.setAttribute("infoId", infoId);
		return "/front/event_info";
	}
	
	/**
	 * 
		 * 此方法描述的是：根据raceId获取详细信息
		 * @author: cwftalus@163.com
		 * @version: 2015年4月17日 上午10:45:27
	 */
	@RequestMapping(value = "/ajaxJumpInfos.do")
	@ResponseBody
	public Response<RaceListDto> loadRaceInfo(@Param("raceId") String raceId){
		Response<RaceListDto> result = new Response<RaceListDto>();
		RaceListDto raceListDto = loadraceInfoById(raceId);
		result.setData(raceListDto);
		return result;
	}

	public RaceListDto loadraceInfoById(String raceId){
		RaceInfoDto raceInfoDto = new RaceInfoDto();
		raceInfoDto.setId(raceId);
		RaceListDto raceListDto = iRaceService.findDetails(raceInfoDto);		
		return raceListDto;
	}
	/**
	 * 
		 * 此方法描述的是：加载赛事对应的比赛排行榜
		 * @author: cwftalus@163.com
		 * @version: 2015年4月18日 下午3:52:40
	 */
	@RequestMapping(value = "/loadRaceScore.do")
	@ResponseBody	
	public Response<HashMap<String,List<RaceScoreboardDto>>> loadRaceScore(String raceId){
		Response<HashMap<String,List<RaceScoreboardDto>>> result = new Response<HashMap<String,List<RaceScoreboardDto>>>();
		List<RaceScoreboardDto> dataList = iRaceScoreboardService.findBy(raceId);
		HashMap<String,List<RaceScoreboardDto>> slMap = new HashMap<String,List<RaceScoreboardDto>>();
		for(RaceScoreboardDto raceScoreboardDto : dataList){
			String keyCode = raceScoreboardDto.getTeamGroup();
			List<RaceScoreboardDto> newList = null;//new ArrayList<RaceScoreboardDto>();
			if(slMap.containsKey(keyCode)){
				newList = slMap.get(keyCode);
				newList.add(raceScoreboardDto);
				slMap.put(keyCode, newList);
			}else{
				newList = new ArrayList<RaceScoreboardDto>();
				newList.add(raceScoreboardDto);
				slMap.put(keyCode, newList);
			}
		}
		result.setData(slMap);
		return result;
	}
	
}
