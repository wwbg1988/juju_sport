package com.juju.sport.user.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.user.dto.UserQuery;
import com.juju.sport.user.dto.UsersDto;
import com.juju.sport.user.mapper.UserExMapper;

@Repository
public class UserQueryDao {
	
	@Autowired
	public UserExMapper mapper;

	public List<UsersDto> findByPage(UserQuery query, PageQuery page) {
		if(StringUtils.isEmpty(query.getRealName())) {
			query.setRealName(null);
		} else {
			query.setRealName("%" + query.getRealName() + "%");
		}
		
		if(StringUtils.isEmpty(query.getUserAccount())) {
			query.setUserAccount(null);
		} else {
			query.setUserAccount("%" + query.getUserAccount() + "%");
		}
		
		return mapper.findByPage(query, page);
	}

	public long count(UserQuery query) {
		
		return mapper.count(query);
	}

}
