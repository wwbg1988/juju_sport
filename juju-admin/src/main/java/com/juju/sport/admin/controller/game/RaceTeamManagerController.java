package com.juju.sport.admin.controller.game;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.juju.sport.common.ext.ExtPageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.game.dto.RaceScoreboardDto;
import com.juju.sport.game.dto.RaceTeamDto;
import com.juju.sport.game.dto.RaceTeamQuery;
import com.juju.sport.game.service.IRaceTeamManagerService;

@Controller
@RequestMapping(value="/game/team" )
public class RaceTeamManagerController {

	protected static final Log logger = LogFactory.getLog(RaceTeamManagerController.class);
	
	@Autowired
	private IRaceTeamManagerService teamManagerService;
	
	@ResponseBody
	@RequestMapping(value="/findRaceTeam.do")
	public PageResult<RaceTeamDto> findRaceTeam(RaceTeamQuery query, ExtPageQuery page) {
		logger.info("TeamManagerController Query : " + query);
		
		return teamManagerService.findRaceTeam(query, page.changeToPageQuery());		
	}
	
	public PageResult<RaceTeamDto> findRaceTeamNotInScoreboard(RaceTeamQuery query, ExtPageQuery page) {
		logger.info("findRaceTeamNotInScoreboard Query : " + query);
		
		return teamManagerService.findRaceTeamNotInScoreboard(query.getRaceId(), page.changeToPageQuery());
	}
 
	@ResponseBody
	@RequestMapping(value="/findNotEnterForRaceTeam.do")
	public PageResult<RaceTeamDto> findNotEnterForRaceTeam(RaceTeamQuery query, ExtPageQuery page) {
		logger.info("TeamManagerController Query : " + query);
		
		return teamManagerService.findNotEnterForRaceTeam(query, page.changeToPageQuery());		
	}
	
	@ResponseBody
	@RequestMapping(value="/addRaceTeam.do")
	public RequestResult addRaceTeam(RaceTeamQuery query) {
		logger.info("RaceTeamManagerController Query : " + query);
		teamManagerService.addRaceTeam(query);
		return new RequestResult(true, "添加成功!");
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteRaceTeam.do")
	public RequestResult deleteRaceTeam(RaceTeamQuery query) {
		logger.info("RaceTeamManagerController Query : " + query);
		teamManagerService.deleteRaceTeam(query);
		return new RequestResult(true, "删除成功!");
	}
	
	@ResponseBody
	@RequestMapping(value="/addRaceScoreboard.do")
	public RequestResult addRaceScoreboard(RaceTeamQuery query) {
		teamManagerService.addRaceScoreboard(query);
		return new RequestResult(true, "添加成功!");
	}
	
	@ResponseBody
	@RequestMapping(value="/findRaceScroeboard.do")
	public PageResult<RaceScoreboardDto> findRaceScroeboard(RaceTeamQuery query, ExtPageQuery page) {
		
		return teamManagerService.findRaceScoreboard(query, page.changeToPageQuery());
	}
	
	@ResponseBody
	@RequestMapping(value="/findRaceTeamNotInScoreboard.do")
	public PageResult<RaceTeamDto> findRaceTeamNotInScoreboard(String raceId, ExtPageQuery page) {
		
		return teamManagerService.findRaceTeamNotInScoreboard(raceId, page.changeToPageQuery());
	}
	
	@ResponseBody
	@RequestMapping(value="/updateRaceScoreboard.do")
	public RequestResult updateRaceScoreboard(RaceScoreboardDto scoreboard) {	
		teamManagerService.updateRaceScoreboard(scoreboard);
		return new RequestResult(true, "修改成功!");
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteRaceScoreboard.do")
	public RequestResult deleteRaceScoreboard(String id) {
		teamManagerService.deleteRaceScoreboard(id);
		return new RequestResult(true, "删除成功!");
	}

}
