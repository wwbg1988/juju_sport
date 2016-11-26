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
import com.juju.sport.user.dto.MedalDto;
import com.juju.sport.user.service.IMedalManagerService;

/**
 * @author rkzhang
 */
@Controller
@RequestMapping(value="/user/medal" )
public class MedalManagerController {

	protected static final Log logger = LogFactory.getLog(MedalManagerController.class);
	
	@Autowired
	private IMedalManagerService medalService;
	
	@RequestMapping(value="/index.do")
	public String index(){
        logger.debug("visit admin user manager index");
        return "/admin/user/medal";
    }
	
	
	@ResponseBody
	@RequestMapping(value="/findAll.do")
	public PageResult<MedalDto> findAll(ExtPageQuery page) {
			
		return medalService.findAll(page.changeToPageQuery());
	}
	
	@ResponseBody
	@RequestMapping(value="/create.do")
	public String createMedal(MedalDto medal, @RequestParam("picImage")MultipartFile myfile) throws IOException {
		logger.info("Create Medal : " + medal);
		String fileName = FileUtils.uploadImage(myfile);
		medal.setLogo(fileName);
		medalService.createMedal(medal);
    	return JsonUtil.getJsonStr(new RequestResult(true, "添加成功"));
	}
	
	@ResponseBody
	@RequestMapping(value="/delete.do")
	public RequestResult deleteMedal(@RequestParam("medalId")String medalId) {
		
		return medalService.deleteMedal(medalId);
	}
}
