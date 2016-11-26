package com.juju.sport.admin.manager.service;

import org.springframework.stereotype.Service;

import com.juju.sport.admin.manger.dto.AdminUserLoginInfoDto;

@Service
public interface IAdminAccountLoginService {

	AdminUserLoginInfoDto findAdminUserLoginInfo(String accountCode);
	
	AdminUserLoginInfoDto initAdminUserLoginInfo(String accountCode);
	
}
