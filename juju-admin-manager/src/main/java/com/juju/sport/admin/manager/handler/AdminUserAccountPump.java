package com.juju.sport.admin.manager.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.juju.sport.admin.manager.service.IAdminAccountLoginService;
import com.juju.sport.admin.manager.service.IAdminAccountService;
import com.juju.sport.admin.manger.dto.AdminAccountDto;
import com.juju.sport.admin.manger.dto.AdminUserLoginInfoDto;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;

/**
 * 初始化管理员账号信息，包括权限等。
 * @author rkzhang
 */
@Service
public class AdminUserAccountPump implements Runnable{

	private static final int PAGE_SIZE = 20;

	protected static final Log logger = LogFactory.getLog(AdminUserAccountPump.class);
	
	@Autowired
	private IAdminAccountLoginService loginService;
	
	@Autowired
	private IAdminAccountService accountService;
	
	@Override
	public void run() {
		logger.info("prepare admin account info --- ");
		long total = accountService.countAll();
		
		PageQuery page = new PageQuery(total, PAGE_SIZE);
		PageResult<AdminAccountDto> accounts = accountService.findAllByPage(page);
		while(!CollectionUtils.isEmpty(accounts.getResults())){
			for(AdminAccountDto account : accounts.getResults()){
				try{
					AdminUserLoginInfoDto loginInfo = loginService.initAdminUserLoginInfo(account.getUserName());
					logger.debug("push to cash --- " + loginInfo);
					Thread.sleep(200);
				}catch(Throwable e){
					e.printStackTrace();
					continue;
				}
			}
			
			page.nextPage();
			accounts = accountService.findAllByPage(page);
		}
	}

}
