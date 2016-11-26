package com.juju.sport.base.dao;

import java.util.List;

import lombok.Getter;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.base.dto.SportTypeDto;
import com.juju.sport.base.mapper.SportTypeExMapper;
import com.juju.sport.base.mapper.SportTypeMapper;
import com.juju.sport.base.pojo.SportType;
import com.juju.sport.base.pojo.SportTypeExample;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.mybatis.MyBatisBaseDao;

@Repository
public class SportTypeDao extends MyBatisBaseDao<SportType> {

	@Autowired
	@Getter
	private SportTypeMapper mapper;
	
	@Autowired
	@Getter
	private SportTypeExMapper exMapper;
	
	public List<SportType> findBy(SportTypeDto sportTypeDto) {
		SportTypeExample example = new SportTypeExample();
		SportTypeExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		criteria.andIdEqualTo("7");
		example.setOrderByClause(" id ASC ");
	
		return mapper.selectByExample(example);
	}

	public List<SportType> findAll() {
		SportTypeExample example = new SportTypeExample();
		SportTypeExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause(" id ASC ");
		return mapper.selectByExample(example);
	}
	public List<SportTypeDto> findByStad(String userId){
		return exMapper.findByStad(userId);
	}
}
