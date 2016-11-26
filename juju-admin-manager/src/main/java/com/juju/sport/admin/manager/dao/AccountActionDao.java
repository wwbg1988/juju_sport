package com.juju.sport.admin.manager.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.Getter;

import com.juju.sport.admin.manager.mapper.AccountActionMapper;
import com.juju.sport.admin.manager.pojo.AccountAction;
import com.juju.sport.common.mybatis.MyBatisBaseDao;

@Repository
public class AccountActionDao extends MyBatisBaseDao<AccountAction>{

	@Autowired
	@Getter
	private AccountActionMapper mapper;
	
	
	
}
