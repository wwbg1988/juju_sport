package com.juju.sport.family.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.family.mapper.HealthMapper;
import com.juju.sport.family.pojo.Health;
import com.juju.sport.family.pojo.HealthExample;



@Repository
public class HealthDao extends MyBatisBaseDao<Health>{

	@Autowired
    @Getter
    private HealthMapper hmapper;
	
	public Health getHealth(){
		HealthExample he =new HealthExample();
		HealthExample.Criteria criteria = he.createCriteria();
		criteria.andHealthKnowledgeIsNotNull();
		List<Health> lh=hmapper.selectByExample(he);
		if(lh.size()>0){
			return  lh.get(0);
		}
		return null;
	}
	
	@Override
	public Object getMapper() {
		// TODO Auto-generated method stub
		return null;
	}


}
