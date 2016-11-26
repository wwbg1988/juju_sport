package com.juju.sport.base.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.base.mapper.HomePageConfigMapper;
import com.juju.sport.base.pojo.HomePageConfig;
import com.juju.sport.base.pojo.HomePageConfigExample;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.mybatis.MyBatisBaseDao;

@Repository
public class HomePageConfigDao extends MyBatisBaseDao<HomePageConfig> {
	
	@Autowired
	@Getter
	private HomePageConfigMapper mapper;

	public List<HomePageConfig> findAll() {
		HomePageConfigExample example = new HomePageConfigExample();
		HomePageConfigExample.Criteria criteria = example.createCriteria();		
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause(" order_tag asc ");
		return mapper.selectByExample(example);
	}

	public List<HomePageConfig> findByKey(String key) {
		HomePageConfigExample example = new HomePageConfigExample();
		HomePageConfigExample.Criteria criteria = example.createCriteria();	
		criteria.andDataKeyEqualTo(key);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause(" order_tag asc ");
		return mapper.selectByExample(example);
	}

	public void logicDeleteById(String configId) {
		HomePageConfig config = new HomePageConfig();
		config.setId(configId);
		config.setStat(DataStatus.DISABLED);
		mapper.updateByPrimaryKeySelective(config);
	}
	
	public int countAll() {
		HomePageConfigExample example = new HomePageConfigExample();
		HomePageConfigExample.Criteria criteria = example.createCriteria();	
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}
	
}
