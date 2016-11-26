package com.juju.sport.admin.manager.dao;

import com.juju.sport.admin.manager.mapper.RoleMapper;
import com.juju.sport.admin.manager.pojo.Role;
import com.juju.sport.admin.manager.pojo.RoleExample;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.mybatis.MyBatisBaseDao;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDao extends MyBatisBaseDao<Role>{

	@Autowired
	@Getter
	private RoleMapper mapper;

    public List<Role> findAll(){
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause(" create_time asc ");
        return mapper.selectByExample(example);
    }

	public int logicDelete(String roleId) {
		RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        criteria.andIdEqualTo(roleId);
        Role record = new Role();
        record.setStat(DataStatus.DISABLED);
		return mapper.updateByExampleSelective(record, example);
        
	}
	
}
