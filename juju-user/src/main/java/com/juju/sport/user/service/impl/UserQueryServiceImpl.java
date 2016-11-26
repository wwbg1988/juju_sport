package com.juju.sport.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.juju.sport.base.cache.IAddressesCache;
import com.juju.sport.base.cache.ISportTypeCache;
import com.juju.sport.base.constants.Job;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.util.StringUtils;
import com.juju.sport.user.api.IUserQueryService;
import com.juju.sport.user.dao.UserQueryDao;
import com.juju.sport.user.dto.UserQuery;
import com.juju.sport.user.dto.UsersDto;

@Service
public class UserQueryServiceImpl implements IUserQueryService {
	
	@Autowired
	public UserQueryDao userQueryDao;
	
	@Autowired
	public IAddressesCache addressesCache;
	
	@Autowired
	public ISportTypeCache sportTypeCache;

	@Override
	public PageResult<UsersDto> find(UserQuery query, PageQuery page) {
		List<UsersDto> results = userQueryDao.findByPage(query, page);
		if(!CollectionUtils.isEmpty(results)) {
			for(UsersDto result : results) {
				setExValue(result);
			}
		}
		long total = userQueryDao.count(query);
		page.setTotal(total);
		return new PageResult<UsersDto>(page, results);
	}

	private void setExValue(UsersDto result) {
		result.setJobName(Job.getDesc(result.getJob()));
		result.setCountryName(addressesCache.findNameById(result.getCountryid()));
		result.setProvinceName(addressesCache.findNameById(result.getProvinceid()));
		result.setCityName(addressesCache.findNameById(result.getCityid()));
		if(StringUtils.isNotEmpty(result.getVenueType())){
			result.setVenueTypeNames(sportTypeCache.getNameStr(result.getVenueType()));
		}
	}

}
