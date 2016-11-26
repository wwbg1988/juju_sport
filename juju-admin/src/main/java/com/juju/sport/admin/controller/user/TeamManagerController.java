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
import com.juju.sport.user.dto.TeamDto;
import com.juju.sport.user.dto.TeamQuery;
import com.juju.sport.user.service.ITeamManagerService;

/**
 * @author rkzhang
 */
@Controller
@RequestMapping(value="/user/team" )
public class TeamManagerController {
	
	protected static final Log logger = LogFactory.getLog(TeamManagerController.class);
	
	@Autowired
	private ITeamManagerService teamManagerService;

	@RequestMapping(value="/index.do")
	public String index(){
        logger.debug("visit admin user manager index");
        return "/admin/user/team-manage";
    }
	
	@ResponseBody
	@RequestMapping(value="/find.do")
	public PageResult<TeamDto> find(TeamQuery query, ExtPageQuery page) {
		PageResult<TeamDto> results = teamManagerService.findTeamByPage(query, page.changeToPageQuery());
		return results;
	}
	
	@ResponseBody
	@RequestMapping(value="/create.do")
	public String create(TeamDto dto, @RequestParam("picImage")MultipartFile myfile) throws IOException {
		logger.info(" create : " + dto);
		String filePath = FileUtils.uploadImage(myfile);
		dto.setThumbnail(filePath);
		teamManagerService.createTeam(dto);
		return JsonUtil.getJsonStr(new RequestResult(true, "200"));
	}
	
	@ResponseBody
	@RequestMapping(value="/delete.do")
	public RequestResult delete(@RequestParam("teamId")String teamId) {
		logger.info(" delete : " + teamId);
		teamManagerService.delete(teamId);
		return new RequestResult(true, "禁用成功");
	}
	
	@ResponseBody
	@RequestMapping(value="/update.do")
	public String update(TeamDto dto, @RequestParam("picImage")MultipartFile myfile) throws IOException {
		logger.info(" update : " + dto);
		if(myfile != null){
			String filePath = FileUtils.uploadImage(myfile);
			dto.setThumbnail(filePath);
		}
		teamManagerService.updateTeam(dto);
		return JsonUtil.getJsonStr(new RequestResult(true, "200"));
	}
}
