package com.juju.sport.admin.controller.manage;

import java.util.List;

import com.juju.sport.admin.manager.service.IRoleCURDService;
import com.juju.sport.admin.manager.service.IRoleManagerService;
import com.juju.sport.admin.manger.dto.RoleActionDto;
import com.juju.sport.admin.manger.dto.RoleDto;
import com.juju.sport.admin.manger.dto.RoleMenuMappingDto;
import com.juju.sport.common.jdbc.DataSourceHolderUtil;
import com.juju.sport.common.model.ListResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.common.util.JsonUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by rkzhang on 14-9-12.
 */
@Controller
@RequestMapping(value="/role")
public class RoleManagerController {

    protected static final Log logger = LogFactory.getLog(RoleManagerController.class);

    @Autowired
    private IRoleManagerService roleService;
    
    @Autowired
    private IRoleCURDService roleCurdService;

    @RequestMapping(value="/index.do")
    public String index(){
        logger.debug("visit role manager page");
        return "/admin/manager/role";
    }

    @ResponseBody
    @RequestMapping(value="/findAll.do")
    public ListResult<RoleDto> findAll(){
        return new ListResult<RoleDto>(true, roleService.findAll());
    }

    @ResponseBody
    @RequestMapping(value="/create.do")
    public RequestResult create(RoleDto role){
    	return roleCurdService.create(role);
    }
    
    @ResponseBody
    @RequestMapping(value="/getRoleMenu.do")
    public ListResult<RoleMenuMappingDto> getRoleMenuByRoleId(@RequestParam("roleId")String roleId){
		return roleService.getRoleMenuByRoleId(roleId);
	} 
    
    @ResponseBody
    @RequestMapping(value="/updateRoleMenu.do")
    public RequestResult updateRoleMenu(@RequestParam("roleId")String roleId, @RequestParam("menuIds")String menuIds){
    	List<String> menuIdList = JsonUtil.getObjFromJsonArray(menuIds, String[].class);
    	return roleService.updateRoleMenu(roleId, menuIdList);
    }
    
    @ResponseBody
    @RequestMapping(value="/getRoleAction.do")
    public ListResult<RoleActionDto> getRoleActionByRoleId(@RequestParam("roleId")String roleId) {
    	return roleService.getRoleActionByRoleId(roleId);
    }
    
    @ResponseBody
    @RequestMapping(value="/updateRoleAction.do")
    public RequestResult updateRoleAction(@RequestParam("roleId")String roleId, @RequestParam("actionIds")String actionIds){
    	List<String> actionIdList = JsonUtil.getObjFromJsonArray(actionIds, String[].class);
    	return roleService.updateRoleAction(roleId, actionIdList);
    }
    
	@ResponseBody
	@RequestMapping(value="/delete.do")
	public RequestResult delete(String roleId) {
		return roleService.deleteById(roleId);
	}
}
