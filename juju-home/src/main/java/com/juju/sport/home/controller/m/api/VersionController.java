package com.juju.sport.home.controller.m.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.base.dto.AppVersionDto;
import com.juju.sport.base.service.IAppVersionService;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.Response;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.user.dto.VenusInfoDto;

/**
 * 
	 * 此类描述的是：给移动端提供版本跟新接口
	 * @author: cwftalus@163.com
	 * @version: 2015年4月30日 下午2:06:06
 */
@Controller
@RequestMapping(value = "/api/m/version")
public class VersionController {
	
	@Autowired
	private IAppVersionService iAppVersionService;
	
	@ResponseBody
	@RequestMapping("/query.do")
	public Response<AppVersionDto> queryVenuesByPage(AppVersionDto appDto){
		Response<AppVersionDto> result = new Response<AppVersionDto>();
		if(appDto!=null){
			if(!StringUtils.isEmpty(String.valueOf(appDto.getVersionNum())) && !StringUtils.isEmpty(appDto.getVersionType())){
				AppVersionDto appVersionDto = iAppVersionService.queryBy(appDto);
				result.setData(appVersionDto);
			}			
		}
		return result;
	}
}
