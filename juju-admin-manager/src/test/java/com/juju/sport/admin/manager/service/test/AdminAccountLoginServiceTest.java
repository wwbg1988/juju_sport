package com.juju.sport.admin.manager.service.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.juju.sport.admin.manager.service.IAdminAccountLoginService;
import com.juju.sport.admin.manager.test.BaseTestCase;
import com.juju.sport.admin.manger.dto.AdminUserLoginInfoDto;
import com.juju.sport.common.redis.WdRedisDao;

public class AdminAccountLoginServiceTest extends BaseTestCase {
	
	@Autowired
	private IAdminAccountLoginService loginService;
	
	@Autowired
	private WdRedisDao<AdminUserLoginInfoDto> loginInfoRedisDao;
	
	@Test
	public void findAdminUserLoginInfo(){
		String accountCode = "admin999";
		
		AdminUserLoginInfoDto userLoginInfo = loginService.findAdminUserLoginInfo(accountCode);
		userLoginInfo = loginInfoRedisDao.get(userLoginInfo.getAccountCode(), AdminUserLoginInfoDto.class);
		System.out.println(userLoginInfo);
	}
}
