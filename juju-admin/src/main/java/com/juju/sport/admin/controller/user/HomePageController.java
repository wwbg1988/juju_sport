package com.juju.sport.admin.controller.user;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.admin.manager.service.IHomePageManageService;
import com.juju.sport.base.dto.PageData;
import com.juju.sport.base.service.IHomePageConfigSerivce;
import com.juju.sport.common.model.ListResult;
import com.juju.sport.common.model.RequestResult;

/**
 * @author rkzhang
 */
@Controller
@RequestMapping(value="/user/homepage" )
public class HomePageController {

	protected static final Log logger = LogFactory.getLog(HomePageController.class);
	
	@Autowired
	private IHomePageConfigSerivce homePageConfigSerivce;
	
	@Autowired
	private IHomePageManageService homePageManageService;
	
	@RequestMapping(value="/index.do")
	public String index(){
        logger.debug("visit admin user manager index");
        return "/admin/user/home-page";
    }
	
	@ResponseBody
	@RequestMapping(value="/find.do")
	public ListResult<PageData> find(@Param("key")String key) {
		List<PageData> results = homePageConfigSerivce.findByKey(key);
		return new ListResult<>(results);
	}
	
	@ResponseBody
	@RequestMapping(value="/add.do")
	public RequestResult add(@Param("venuesId")String venuesId, @Param("pageKey")String pageKey) {
		
		return homePageManageService.addVenuesToHomePage(venuesId, pageKey);
	}
	
	@ResponseBody
	@RequestMapping(value="/delete.do")
	public RequestResult delete(@Param("configId")String configId) {
		
		return homePageConfigSerivce.deleteById(configId);
	}
}
