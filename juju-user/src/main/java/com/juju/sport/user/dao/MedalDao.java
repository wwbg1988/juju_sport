package com.juju.sport.user.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.user.mapper.MedalMapper;
import com.juju.sport.user.pojo.Medal;
import com.juju.sport.user.pojo.MedalExample;
import com.juju.sport.user.pojo.MedalExample.Criteria;

@Repository
public class MedalDao extends MyBatisBaseDao<Medal> {

	@Autowired
	@Getter 
	private MedalMapper mapper;

	public List<Medal> findAll(PageQuery page) {
		MedalExample example = new MedalExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);	
		example.setOrderByClause(" type, level ");
		return mapper.selectByExample(example);
	}
	
	public int countAll() {
		MedalExample example = new MedalExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);		
		return mapper.countByExample(example);
	}

	public int logicDeleteById(String medalId) {
		Medal medal = new Medal();
		medal.setId(medalId);
		medal.setStat(DataStatus.DISABLED);
		return mapper.updateByPrimaryKeySelective(medal);
	}
	
}
