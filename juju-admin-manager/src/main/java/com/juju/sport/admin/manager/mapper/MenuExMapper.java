package com.juju.sport.admin.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.juju.sport.admin.manager.pojo.Menu;

public interface MenuExMapper {

	List<Menu> findByAdminAccountId(@Param("accountId")String accountId, @Param("roleId")String roleId);
	
}
