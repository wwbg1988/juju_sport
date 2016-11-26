package com.juju.sport.stadium.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.base.mapper.VenuesSerViceTypeMapper;
import com.juju.sport.base.pojo.VenuesSerViceType;
import com.juju.sport.base.pojo.VenuesSerViceTypeExample;
import com.juju.sport.common.mybatis.MyBatisBaseDao;


@Repository
public class VenuesServiceTypeDao extends MyBatisBaseDao<VenuesSerViceType>{
	@Autowired
	@Getter
	private VenuesSerViceTypeMapper mapper;	
	public List<VenuesSerViceType> selectServiceTypeByVenusInfoId(String id){
		VenuesSerViceTypeExample example = new VenuesSerViceTypeExample();
		VenuesSerViceTypeExample.Criteria criteria = example.createCriteria();
		criteria.andVenuesInfoIdEqualTo(id);   //消息类型为场馆消息
		return mapper.selectByExample(example);
	}
}
