package com.juju.sport.admin.controller.manage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juju.sport.admin.manager.service.IAdminAccountService;
import com.juju.sport.admin.manger.dto.AdminAccountDto;
import com.juju.sport.admin.manger.dto.AdminAccountQuery;
import com.juju.sport.admin.manger.dto.AdminUserLoginInfoDto;
import com.juju.sport.admin.manger.dto.ResetPasswdQuery;
import com.juju.sport.common.constants.SessionConstants;
import com.juju.sport.common.ext.ExtPageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;

/**
 * @author rkzhang
 */
@Controller
@RequestMapping(value="/manager/account")
public class AdminAccountManageController {

	protected static final Log logger = LogFactory.getLog(AdminAccountManageController.class);

	@Autowired
	private IAdminAccountService adminAccountService;
	
	@RequestMapping(value="/index.do")
    public String index(){
        logger.debug("visit admin account manager page");
        return "/admin/manager/account";
    }
	
	@RequestMapping(value="/find.do")
	@ResponseBody
	public PageResult<AdminAccountDto> find(AdminAccountQuery query, ExtPageQuery page) {
		return adminAccountService.find(query, page.changeToPageQuery());
	}
	
	@RequestMapping(value="/saveOrUpdate.do")
	@ResponseBody
	public RequestResult saveOrUpdate(AdminAccountDto account) {
		
		return adminAccountService.save(account);
	}
	
	@RequestMapping(value="/delete.do")
	@ResponseBody
	public RequestResult delete(@RequestParam("accountId")String accountId) {
		
		return adminAccountService.delete(accountId);
	}
	
	@RequestMapping(value="/resetDefaultPasswd.do")
	@ResponseBody
	public RequestResult resetDefaultPasswd(@RequestParam("accountId")String accountId) {
		
		return adminAccountService.resetPasswd(accountId);
	}
	
	@RequestMapping(value="/resetPasswd.do")
	@ResponseBody
	public RequestResult resetPasswd(ResetPasswdQuery query, HttpServletRequest request) {
		HttpSession session = request.getSession();
		AdminUserLoginInfoDto user = (AdminUserLoginInfoDto)session.getAttribute(SessionConstants.ADMIN_USER_ID);
		query.setAccountCode(user.getAccountCode());
		return adminAccountService.resetPasswd(query);
	}
	
}
