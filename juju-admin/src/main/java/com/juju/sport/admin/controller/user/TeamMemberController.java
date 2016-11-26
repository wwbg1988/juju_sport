package com.juju.sport.admin.controller.user;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.juju.sport.common.ext.ExtPageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.common.util.FileUtils;
import com.juju.sport.common.util.JsonUtil;
import com.juju.sport.user.dto.TeamMemberDto;
import com.juju.sport.user.dto.TeamMemberQuery;
import com.juju.sport.user.dto.UsersDto;
import com.juju.sport.user.service.ITeamMemberService;

/**
 * @author rkzhang
 */
@Controller
@RequestMapping(value="/user/team-member" )
public class TeamMemberController {
	
	protected static final Log logger = LogFactory.getLog(TeamMemberController.class);
	
	@Autowired
	private ITeamMemberService teamMemberService;
	
	@ResponseBody
	@RequestMapping(value="/create.do")
	public String create(TeamMemberDto dto, @RequestParam("picImage")MultipartFile myfile) throws IOException {		
		String fileName = FileUtils.uploadImage(myfile);
		dto.setPic(fileName);
		teamMemberService.create(dto);
		return JsonUtil.getJsonStr(new RequestResult(true, "200"));
	}
	
	@ResponseBody
	@RequestMapping(value="/addUserJoinTeam.do")
	public RequestResult addUserJoinTeam(@RequestParam("userId")String userId, @RequestParam("teamId")String teamId) {
		
		return teamMemberService.addUserJoinTeam(userId, teamId);
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteTeamMember.do")
	public RequestResult deleteTeamMember(@RequestParam("memberId")String memberId) {
		
		return teamMemberService.deleteTeamMember(memberId);
	}
	
	@ResponseBody
	@RequestMapping(value="/findUserNotInTeam.do")
	public PageResult<UsersDto> findUserNotInTeam(TeamMemberQuery query, ExtPageQuery page) {
		
		return teamMemberService.findUserNotInTeam(query, page.changeToPageQuery());
	}
	
	@ResponseBody
	@RequestMapping(value="/findMemberByTeamId.do")
	public PageResult<TeamMemberDto> findMemberByTeamId(@RequestParam("teamId")String teamId, ExtPageQuery page) {
		
		return teamMemberService.findMemberByTeamId(teamId, page.changeToPageQuery());
	}
	
	@ResponseBody
	@RequestMapping(value="/updateTeamMember.do")
	public String updateTeamMember(TeamMemberDto dto, @RequestParam("picImage")MultipartFile myfile) throws IOException {
		String fileName = FileUtils.uploadImage(myfile);
		dto.setPic(fileName);
		teamMemberService.updateTeamMember(dto);
		return JsonUtil.getJsonStr(new RequestResult(true, "200"));
	}
}
