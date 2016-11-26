package com.juju.sport.family.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.family.mapper.SpecificationsMapper;
import com.juju.sport.family.pojo.Specifications;
import com.juju.sport.family.pojo.SpecificationsExample;


@Repository
public class SpecificationsDao extends MyBatisBaseDao<Specifications>{
	
	@Autowired
    @Getter
    private SpecificationsMapper smapper;

	
	public Specifications getSpecifications(String sex, int age){
		SpecificationsExample se = new SpecificationsExample();
		SpecificationsExample.Criteria criteria = se.createCriteria();
		criteria.andAgeEqualTo(age);
		criteria.andSexEqualTo(sex);
		List<Specifications> ls = smapper.selectByExample(se);
		if(ls.size()>0){
			return ls.get(0);
		}
		return null;
	}


	@Override
	public Object getMapper() {
		// TODO Auto-generated method stub
		return null;
	}
}
