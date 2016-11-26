package com.juju.sport.admin.controller.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.common.ext.ExtPageQuery;
import com.juju.sport.common.jdbc.DataSourceHolderUtil;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.user.api.IUserManagerService;
import com.juju.sport.user.api.IUserQueryService;
import com.juju.sport.user.dto.UserQuery;
import com.juju.sport.user.dto.UsersDto;

/**
 * 
 * @author rkzhang
 *
 */
@Controller
@RequestMapping(value="/user/manager" )
public class UserManagerController {
	
	protected static final Log logger = LogFactory.getLog(UserManagerController.class);
	
	@Autowired
	public IUserQueryService userQueryService;
	
	@Autowired
	public IUserManagerService userManagerService;
	
	@RequestMapping(value="/index.do")
	public String index(){
        logger.debug("visit admin user manager index");
        return "/admin/user/user-manage";
    }
	
	@ResponseBody
	@RequestMapping(value="/find.do")
	public PageResult<UsersDto> find(UserQuery query, ExtPageQuery page) {
		logger.info("UserManagerController Query : " + query);
		PageResult<UsersDto> results = userQueryService.find(query, page.changeToPageQuery());
		return results;
	}
	
	@ResponseBody
	@RequestMapping(value="/update.do")
	public RequestResult update(UsersDto user) {
		DataSourceHolderUtil.setToMaster();
		return userManagerService.update(user);
	}
	
	@ResponseBody
	@RequestMapping(value="/delete.do")
	public RequestResult delete(String id) {
		DataSourceHolderUtil.setToMaster();
		return userManagerService.deleteById(id);
	}

}
