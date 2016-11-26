package com.juju.sport.admin.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.base.constants.Job;
import com.juju.sport.base.constants.WarType;
import com.juju.sport.base.service.ISportTypeService;
import com.juju.sport.common.dto.Pair;
import com.juju.sport.common.model.ListResult;
import com.juju.sport.game.dto.RaceInfoDto;
import com.juju.sport.user.constants.UserStat;
import com.juju.sport.user.dto.MedalDto;
import com.juju.sport.user.dto.TeamMemberDto;

/**
 * 提供工作类型等公用的常量信息
 * @author rkzhang
 */
@Controller
@RequestMapping(value="/common/constants")
public class CommonConstantsController {
	
	@Autowired
	private ISportTypeService sportTypeService;

	@ResponseBody
	@RequestMapping(value="/jobCategory.do")
	public ListResult<Pair<Integer, String>> getJobCategory() {
		return Job.getComboData();
	}
	
	@ResponseBody
	@RequestMapping(value="/userStat.do")
	public ListResult<Pair<Integer, String>> getUserStat() {
		return UserStat.getComboData();
	}
	
	@ResponseBody
	@RequestMapping(value="/sportType.do")
	public ListResult<Pair<String, String>> getSportType() {
		return sportTypeService.findAllComboData();
	}
	
	@ResponseBody
	@RequestMapping(value="/infoType.do")
	public ListResult<Pair<Integer, String>> getInfoType() {
		return RaceInfoDto.InfoType.getComboData();
	}
	
	@ResponseBody
	@RequestMapping(value="/warType.do")
	public ListResult<Pair<Integer, String>> getWarType() {
		return WarType.getComboData();
	}
		
	@ResponseBody
	@RequestMapping(value="/teamPosition.do")
	public ListResult<Pair<Integer, String>> getTeamPosition() {
		return TeamMemberDto.TeamPosition.getComboData();
	}
	
	@ResponseBody
	@RequestMapping(value="/medalType.do")
	public ListResult<Pair<Integer, String>> getMedalType() {
		return MedalDto.MedalType.getComboData();
	}
}
