package com.juju.sport.admin.manger.dao.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.juju.sport.admin.manager.dto.test.TestDto;
import com.juju.sport.admin.manager.test.BaseTestCase;
import com.juju.sport.common.redis.WdRedisDao;

public class WdRedisDaoTest extends BaseTestCase {

	@Autowired
	private WdRedisDao<TestDto> redisDao;
	
	@Test
	public void testRedisDao(){
		TestDto dto = new TestDto();
		dto.setPassword("aaaaa");
		dto.setUserName("sdfasdf");
		redisDao.set(dto);
		TestDto g = redisDao.get(dto.getUserName(), TestDto.class);
		System.out.println(g);
	}
}
