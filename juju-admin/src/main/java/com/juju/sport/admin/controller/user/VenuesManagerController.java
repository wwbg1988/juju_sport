package com.juju.sport.admin.controller.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.juju.sport.common.ext.ExtPageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.user.dto.VenuesQuery;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.service.IVenuesInfoService;

/**
 * 
 * @author rkzhang
 *
 */
@Controller
@RequestMapping(value="/user/venues" )
public class VenuesManagerController {

	protected static final Log logger = LogFactory.getLog(VenuesManagerController.class);
	
	@Autowired
	private IVenuesInfoService venuesInfoService;
	
	@RequestMapping(value="/index.do")
	public String index(){
        logger.debug("visit admin user manager index");
        return "/admin/user/venues";
    }
	
	@ResponseBody
	@RequestMapping(value="/find.do")
	public PageResult<VenusInfoDto> find(VenuesQuery query, ExtPageQuery page) {
		
		return venuesInfoService.find(query, page.changeToPageQuery());
	}
	 
	@ResponseBody
	@RequestMapping(value="/update.do")
	public RequestResult update(VenusInfoDto user) {
		
		return venuesInfoService.update(user);
	}
	
	@ResponseBody
	@RequestMapping(value="/delete.do")
	public RequestResult delete(String id) {
		
		return venuesInfoService.deleteById(id);
	}
}
