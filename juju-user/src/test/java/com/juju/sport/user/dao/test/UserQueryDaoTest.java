package com.juju.sport.user.dao.test;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.user.dao.UserQueryDao;
import com.juju.sport.user.dto.UserQuery;
import com.juju.sport.user.dto.UsersDto;
import com.juju.sport.user.test.BaseTestCase;

public class UserQueryDaoTest extends BaseTestCase {
	
	protected static final Log logger = LogFactory.getLog(UserQueryDaoTest.class);

	@Autowired
	private UserQueryDao userQueryDao;
	
	@Test
	public void find() {
		UserQuery query = new UserQuery();
		PageQuery page = new PageQuery();
		query.setRealName("BB%");
		page.setPageSize(20);
		List<UsersDto> results = userQueryDao.findByPage(query, page);
		for(UsersDto user : results) {
			logger.info(user);
		}
		
		long count = userQueryDao.count(query);
		logger.info("total : " + count);
	}
}
