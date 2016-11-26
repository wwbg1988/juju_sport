package com.juju.sport.admin.manager.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.juju.sport.admin.manager.mapper.AdminAccountMapper;
import com.juju.sport.admin.manager.pojo.AdminAccount;
import com.juju.sport.admin.manager.pojo.AdminAccountExample;
import com.juju.sport.admin.manager.pojo.AdminAccountExample.Criteria;
import com.juju.sport.admin.manger.dto.AdminAccountQuery;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.StringUtils;

@Repository
public class AdminAccountDao extends MyBatisBaseDao<AdminAccount>{

	@Autowired
	@Getter
    public AdminAccountMapper mapper;
	
	public AdminAccount findByUsername(String username){
		AdminAccountExample example = new AdminAccountExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo(username);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		
		List<AdminAccount> adminUsers = mapper.selectByExample(example);
		return CollectionUtils.isEmpty(adminUsers) ? null : adminUsers.get(0);
	}

	public long countAll() {
		AdminAccountExample example = new AdminAccountExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}

	public List<AdminAccount> findAllByPage(PageQuery page) {
		AdminAccountExample example = new AdminAccountExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause(" create_time asc limit " + page.getStartNum() + ", " + page.getPageSize());
		return mapper.selectByExample(example);
	}

	public List<AdminAccount> find(AdminAccountQuery query, PageQuery page) {
		AdminAccountExample example = new AdminAccountExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		if(StringUtils.isNotEmpty(query.getAccountCode())) {
			criteria.andUserNameEqualTo(query.getAccountCode());
		}
		if(StringUtils.isNotEmpty(query.getRoleId())) {
			criteria.andRoleIdEqualTo(query.getRoleId());
		}
		example.setOrderByClause(" create_time asc limit " + page.getStartNum() + ", " + page.getPageSize());
		return mapper.selectByExample(example);
	}

	public long count(AdminAccountQuery query) {
		AdminAccountExample example = new AdminAccountExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		if(StringUtils.isNotEmpty(query.getAccountCode())) {
			criteria.andUserNameEqualTo(query.getAccountCode());
		}
		if(StringUtils.isNotEmpty(query.getRoleId())) {
			criteria.andRoleIdEqualTo(query.getRoleId());
		}
		return mapper.countByExample(example);
	}

	public void logicDelete(String accountId) {
		AdminAccountExample example = new AdminAccountExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(accountId);
		AdminAccount account = new AdminAccount();
		account.setStat(0);
		mapper.updateByExampleSelective(account, example);	
	}
	
}
