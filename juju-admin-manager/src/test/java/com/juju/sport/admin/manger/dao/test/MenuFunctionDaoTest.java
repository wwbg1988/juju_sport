package com.juju.sport.admin.manger.dao.test;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.juju.sport.admin.manager.dao.MenuFunctionDao;
import com.juju.sport.admin.manager.pojo.Menu;
import com.juju.sport.admin.manager.test.BaseTestCase;

@Transactional
public class MenuFunctionDaoTest extends BaseTestCase {
	
	protected static final Log logger = LogFactory.getLog(MenuFunctionDaoTest.class);

	@Autowired
	private MenuFunctionDao functionDao;
	
	@Test
	public void findByAdminAccountIdTest(){
		String accountId = "08890bc0-3d39-43c6-986f-846ab5bd65fd";
		String roleId = "b317cb71-5ddd-4c79-a5f6-5ed8f107ce5c";
		List<Menu> menuFunctions = functionDao.findByAdminAccountId(accountId, roleId);
		for(Menu function : menuFunctions){
			System.out.println(function);
		}
	}
}
