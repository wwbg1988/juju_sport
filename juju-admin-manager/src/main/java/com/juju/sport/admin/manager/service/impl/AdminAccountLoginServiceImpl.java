package com.juju.sport.admin.manager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.admin.manager.cache.RoleCache;
import com.juju.sport.admin.manager.dao.MenuFunctionDao;
import com.juju.sport.admin.manager.dao.MenuGroupDao;
import com.juju.sport.admin.manager.dao.RoleDao;
import com.juju.sport.admin.manager.pojo.Menu;
import com.juju.sport.admin.manager.pojo.MenuGroup;
import com.juju.sport.admin.manager.pojo.Role;
import com.juju.sport.admin.manager.service.IAdminAccountLoginService;
import com.juju.sport.admin.manager.service.IAdminAccountService;
import com.juju.sport.admin.manger.dto.AdminAccountDto;
import com.juju.sport.admin.manger.dto.AdminMenuFunctionDto;
import com.juju.sport.admin.manger.dto.AdminMenuGroupDto;
import com.juju.sport.admin.manger.dto.AdminUserLoginInfoDto;
import com.juju.sport.admin.manger.dto.RoleDto;
import com.juju.sport.common.redis.WdRedisDao;

/**
 * 用户查找用户登录名，密码， 菜单权限， 访问权限等一系列信息的
 * @author zhangr01
 *
 */
@Service
public class AdminAccountLoginServiceImpl implements IAdminAccountLoginService {
	
	@Autowired
	private MenuFunctionDao menuFunctionDao;
	
	@Autowired
	private MenuGroupDao menuGroupDao;
	
	@Autowired
	private IAdminAccountService adminAccountService;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private RoleCache roleCache;
	
	@Autowired
	private WdRedisDao<AdminUserLoginInfoDto> loginInfoRedisDao;

	@Override
	public AdminUserLoginInfoDto findAdminUserLoginInfo(String accountCode) {
		AdminUserLoginInfoDto loginInfo = loginInfoRedisDao.get(accountCode, AdminUserLoginInfoDto.class);
		if(loginInfo == null){
			loginInfo = findInDB(accountCode);
            if(loginInfo != null){
                loginInfoRedisDao.set(loginInfo);
            }
		}

		return loginInfo;
	}
	
	public AdminUserLoginInfoDto initAdminUserLoginInfo(String accountCode) {
		AdminUserLoginInfoDto loginInfo = findInDB(accountCode); 
		if(loginInfo != null){
            loginInfoRedisDao.set(loginInfo);
        }
		return loginInfo;
	}

	private AdminUserLoginInfoDto findInDB(String accountCode) {
		AdminAccountDto info = adminAccountService.findAdminAccountByUsername(accountCode);
		if(info == null){
			return null;
		}
		
		RoleDto roleDto = roleCache.get(info.getRoleId());
	
		List<Menu> functions = menuFunctionDao.findByAdminAccountId(info.getId(), info.getRoleId());
		List<MenuGroup> groups = menuGroupDao.findByAdminAccountId(info.getId(), info.getRoleId());
		
		AdminUserLoginInfoDto loginInfo = populateLoginInfo(info, functions, groups);
		if(roleDto != null) {
			loginInfo.setRole(roleDto);
		}
		return loginInfo;
	}

	private AdminUserLoginInfoDto populateLoginInfo(AdminAccountDto info, List<Menu> functions, List<MenuGroup> groups) {
		AdminUserLoginInfoDto loginInfo = new AdminUserLoginInfoDto();
		loginInfo.setAccountCode(info.getUserName());
		loginInfo.setRealName(info.getUserName());
		loginInfo.setPasswd(info.getPasswd());
		
		Role role = roleDao.selectByPrimaryKey(info.getRoleId());
		if(role != null){
			loginInfo.setGroupName(role.getRoleName());
		}
		
		Map<String, AdminMenuGroupDto> groupMap = new HashMap<String, AdminMenuGroupDto>();
		for(MenuGroup group : groups){
			AdminMenuGroupDto menuGroup = changeTo(group);
			loginInfo.getGroups().add(menuGroup);
			groupMap.put(menuGroup.getId(), menuGroup);
		}
		
		for(Menu function : functions){
			AdminMenuFunctionDto menuFunction = changTo(function);
			AdminMenuGroupDto menuGroup = groupMap.get(menuFunction.getMenuGroupId());
			if(menuGroup != null){
				menuGroup.getFunctions().add(menuFunction);
			}
		}
		
		return loginInfo;
	}

	private AdminMenuFunctionDto changTo(Menu function) {
		AdminMenuFunctionDto menuFunction = new AdminMenuFunctionDto();
		BeanUtils.copyProperties(function, menuFunction);
		return menuFunction;
	}

	private AdminMenuGroupDto changeTo(MenuGroup group) {
		AdminMenuGroupDto menuGroup = new AdminMenuGroupDto();
		BeanUtils.copyProperties(group, menuGroup);
		return menuGroup;
	}

}
