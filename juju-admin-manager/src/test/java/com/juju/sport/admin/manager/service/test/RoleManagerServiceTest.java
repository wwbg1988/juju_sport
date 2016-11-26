package com.juju.sport.admin.manager.service.test;

import com.juju.sport.admin.manager.service.IRoleManagerService;
import com.juju.sport.admin.manager.test.BaseTestCase;
import com.juju.sport.admin.manger.dto.RoleDto;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by rkzhang on 14-9-25.
 */
public class RoleManagerServiceTest extends BaseTestCase {

    @Autowired
    private IRoleManagerService roleService;

    @Test
    public void findAllTest(){
        List<RoleDto> roleDtos = roleService.findAll();
        Assert.assertTrue(!CollectionUtils.isEmpty(roleDtos));
    }
    
    @Test
    public void getRoleMenuByRoleId(){
    	String roleId = "b317cb71-5ddd-4c79-a5f6-5ed8f107ce5c";
    	Assert.assertTrue(!CollectionUtils.isEmpty(roleService.getRoleMenuByRoleId(roleId).getResults()));
	} 
}
