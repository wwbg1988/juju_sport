package com.juju.sport.admin.manager.service.impl;

import com.juju.sport.admin.manager.cache.RoleActionCache;
import com.juju.sport.admin.manager.dao.RoleDao;
import com.juju.sport.admin.manager.dao.RoleMenuDao;
import com.juju.sport.admin.manager.handler.AdminUserAccountRedisHandler;
import com.juju.sport.admin.manager.pojo.Role;
import com.juju.sport.admin.manager.pojo.RoleMenu;
import com.juju.sport.admin.manager.service.IRoleActionService;
import com.juju.sport.admin.manager.service.IRoleManagerService;
import com.juju.sport.admin.manger.dto.RoleActionDto;
import com.juju.sport.admin.manger.dto.RoleDto;
import com.juju.sport.admin.manger.dto.RoleMenuMappingDto;
import com.juju.sport.common.dto.Pair;
import com.juju.sport.common.model.ListResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rkzhang on 14-9-25.
 */
@Service
public class RoleManagerServiceImpl implements IRoleManagerService {

    @Autowired
    private RoleDao roleDao;
    
    @Autowired
    private RoleMenuDao roleMenuDao;
    
    @Autowired
    private IRoleActionService roleActionSerivce;
    
    @Autowired
	private AdminUserAccountRedisHandler redisHandler;
    
    @Autowired
    private RoleActionCache roleActionCache;

    @Override
    public List<RoleDto> findAll() {
        List<Role> roles = roleDao.findAll();
        return BeanUtils.createBeanListByTarget(roles, RoleDto.class);
    }
    
    public ListResult<RoleMenuMappingDto> getRoleMenuByRoleId(String roleId){
		return new ListResult<RoleMenuMappingDto>(roleMenuDao.getRoleMenuByRoleId(roleId));
	}

	@Transactional
	public RequestResult updateRoleMenu(String roleId, List<String> menuIdList) {
		roleMenuDao.logicDeleteRoleMenuByRoleId(roleId);
		for(String menuId : menuIdList){
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(menuId);
			roleMenuDao.insert(roleMenu);
		}
		redisHandler.reflushAll();
		return new RequestResult(true, "更新菜单权限成功！");
	} 
	
	@Override
	public ListResult<Pair<String, String>> getRoles() {
		List<Pair<String, String>> reuslts = new ArrayList<Pair<String, String>>();
		List<Role> roles = roleDao.findAll();
		for(Role role : roles){
			reuslts.add(new Pair<String, String>(role.getId(), role.getRoleName()));
    	}
		return new ListResult<Pair<String, String>>(reuslts);
	}

	@Override
	public ListResult<RoleActionDto> getRoleActionByRoleId(String roleId) {
		return roleActionSerivce.getRoleActionByRoleId(roleId);
	}

	@Transactional
	public RequestResult updateRoleAction(String roleId, List<String> actionIdList) {
		roleActionSerivce.updateRoleAction(roleId, actionIdList);
		roleActionCache.refresh(roleId);
		return new RequestResult(true, "更新功能权限成功！");
	}

	@Override
	public RequestResult deleteById(String roleId) {
		if(StringUtils.isEmpty(roleId)){
			return new RequestResult(false, "无删除记录！"); 
		}
		//删除角色下的功能权限;
		roleActionSerivce.logicDeleteRoleActionByRoleId(roleId);
		//删除角色下的菜单权限;
		roleMenuDao.logicDeleteRoleMenuByRoleId(roleId);
		//删除角色权限;
		roleDao.logicDelete(roleId);
		roleActionCache.refresh();
		redisHandler.reflushAll();
		return new RequestResult(true, "删除角色成功！");
	}
}
