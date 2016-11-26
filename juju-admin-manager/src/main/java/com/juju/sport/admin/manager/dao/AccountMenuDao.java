package com.juju.sport.admin.manager.dao;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.admin.manager.mapper.AccountMenuMapper;
import com.juju.sport.admin.manager.pojo.AccountMenu;
import com.juju.sport.admin.manager.pojo.AccountMenuExample;
import com.juju.sport.common.mybatis.MyBatisBaseDao;

@Repository
public class AccountMenuDao extends MyBatisBaseDao<AccountMenu>{

	@Autowired
	@Getter
	private AccountMenuMapper mapper;

	public int deleteByMenuId(String menuId) {
		AccountMenuExample example = new AccountMenuExample();
		AccountMenuExample.Criteria criteria = example.createCriteria();
		criteria.andMenuIdEqualTo(menuId);
		return mapper.deleteByExample(example);
	}
	

}
