package com.juju.sport.admin.manger.dao.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.juju.sport.admin.manager.dao.MenuGroupDao;
import com.juju.sport.admin.manager.pojo.MenuGroup;
import com.juju.sport.admin.manager.test.BaseTestCase;

@Transactional
public class MenuGroupDaoTest extends BaseTestCase {

	@Autowired
	private MenuGroupDao groupDao;
	
	@Test
	public void findByAdminAccountIdTest(){
		String accountId = "08890bc0-3d39-43c6-986f-846ab5bd65fd";
		String roleId = "b317cb71-5ddd-4c79-a5f6-5ed8f107ce5c";
		List<MenuGroup> groups = groupDao.findByAdminAccountId(accountId, roleId);
		for(MenuGroup group : groups){
			System.out.println(group);
		}
		
	}
}
