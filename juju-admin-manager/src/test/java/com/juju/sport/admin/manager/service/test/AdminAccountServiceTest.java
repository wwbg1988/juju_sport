package com.juju.sport.admin.manager.service.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.juju.sport.admin.manager.dao.AccountMenuDao;
import com.juju.sport.admin.manager.dao.MenuFunctionDao;
import com.juju.sport.admin.manager.dao.MenuGroupDao;
import com.juju.sport.admin.manager.dao.RoleDao;
import com.juju.sport.admin.manager.dao.RoleMenuDao;
import com.juju.sport.admin.manager.pojo.AccountMenu;
import com.juju.sport.admin.manager.pojo.Menu;
import com.juju.sport.admin.manager.pojo.MenuGroup;
import com.juju.sport.admin.manager.pojo.Role;
import com.juju.sport.admin.manager.pojo.RoleMenu;
import com.juju.sport.admin.manager.test.BaseTestCase;
import com.juju.sport.common.util.UUIDGenerator;

public class AdminAccountServiceTest extends BaseTestCase {



	@Autowired
	private MenuFunctionDao menuFunctionDao;
	
	@Autowired
	private MenuGroupDao menuGroupDao;
	
	@Autowired
	private AccountMenuDao accountMenuDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private RoleMenuDao roleMenuDao;
	
	/**
	 * 测试菜单初始化
	 */
	public void prepareAccountData(){
		String[] groupNames = new String[]{"用户管理", "商家管理", "定单管理", "积分管理"};
		String[] ids = new String[groupNames.length];
		String accountId = "08890bc0-3d39-43c6-986f-846ab5bd65fd";
		
		Role role = new Role();
		String roleId = UUIDGenerator.getUUID();
		role.setRoleDescription("内部后台管理员");
		role.setRoleName("管理员");
		role.setId(roleId);
		roleDao.insert(role);
		
		for(int g = 0; g < groupNames.length; g++){
			String id = UUIDGenerator.getUUID();
			MenuGroup group = new MenuGroup();
			group.setId(id);
			group.setMenuGroupName(groupNames[g]);
			group.setMenuGroupOrder(g);
			ids[g] = id;
			menuGroupDao.insert(group);
			
			for(int i = 0 ; i < 5; i++){
				Menu function = new Menu();
				String fid = UUIDGenerator.getUUID();
				function.setId(fid);
				function.setMenuGroupId(id);
				function.setMenuFunctionAction("www.sina.com");
				function.setMenuFunctionDescript("测试功能");
				function.setMenuFunctionName(group.getMenuGroupName() + "-功能" + i);
				
				menuFunctionDao.insert(function);
				
				AccountMenu accountMenu = new AccountMenu();
				accountMenu.setAdminAccountId(accountId);
				accountMenu.setMenuId(fid);
				accountMenuDao.insert(accountMenu);
				
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setMenuId(fid);
				roleMenu.setRoleId(roleId);
				roleMenuDao.insert(roleMenu);
				
			}
		}	
	}
 
	@Test
    public void addMenu(){
        String accountId = "08890bc0-3d39-43c6-986f-846ab5bd65fd";
        String roleId = "b317cb71-5ddd-4c79-a5f6-5ed8f107ce5c";
        String groupId = "6faee6bf-720a-4ebf-905a-51eac08b5156";
      
        Menu function = new Menu();
        String fid = UUIDGenerator.getUUID();
        function.setId(fid);
        function.setMenuGroupId(groupId);
        function.setMenuFunctionAction("/menu／index.do");
        function.setMenuFunctionDescript("功能管理");
        function.setMenuFunctionName("功能管理");
        
        menuFunctionDao.insert(function);
    }
}
