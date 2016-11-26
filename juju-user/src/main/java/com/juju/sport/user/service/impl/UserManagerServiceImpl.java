package com.juju.sport.user.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.sport.common.model.RequestResult;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.user.api.IUserManagerService;
import com.juju.sport.user.dao.UserAccountDao;
import com.juju.sport.user.dao.UserDao;
import com.juju.sport.user.dto.UsersDto;
import com.juju.sport.user.pojo.Users;

@Service
public class UserManagerServiceImpl implements IUserManagerService {
	
	protected static final Log logger = LogFactory.getLog(UserManagerServiceImpl.class);

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserAccountDao userAccountDao;
	
	@Override
	public RequestResult update(UsersDto user) {
		logger.info("User " + user);
		Users target = new Users();
		BeanUtils.copyProperties(user, target, "userAccount", "venueType", "createTime");
		userDao.updateByPrimaryKeySelective(target);
		
		Users users = userDao.selectByPrimaryKey(user.getId());
		if(1 == target.getStat().intValue()) {
			userAccountDao.enableRecord(users.getUserAccountId());
		} else if (0 == target.getStat().intValue()){ 
			userAccountDao.logicDeleteById(users.getUserAccountId());
		}
		return new RequestResult(true, "更新成功!");
	}

	@Override
	@Transactional
	public RequestResult deleteById(String id) {
		userDao.logicDeleteById(id);	
		Users user = userDao.selectByPrimaryKey(id);
		if(user != null) {
			userAccountDao.logicDeleteById(user.getUserAccountId());
		}
		return new RequestResult(true, "禁用成功");
	}

}
