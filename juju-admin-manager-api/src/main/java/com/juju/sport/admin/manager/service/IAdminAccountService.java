package com.juju.sport.admin.manager.service;

import org.springframework.stereotype.Service;

import com.juju.sport.admin.manger.dto.AdminAccountDto;
import com.juju.sport.admin.manger.dto.AdminAccountQuery;
import com.juju.sport.admin.manger.dto.ResetPasswdQuery;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;

@Service
public interface IAdminAccountService {

	AdminAccountDto findAdminAccountByUsername(String username);
	
	long countAll();
	
	PageResult<AdminAccountDto> findAllByPage(PageQuery page);
	
	PageResult<AdminAccountDto> find(AdminAccountQuery query,PageQuery page);
	
	RequestResult save(AdminAccountDto account);
	
	RequestResult update(AdminAccountDto account);

	RequestResult delete(String accountId);

	RequestResult resetPasswd(String accountId);

	RequestResult resetPasswd(ResetPasswdQuery query);
	
}
