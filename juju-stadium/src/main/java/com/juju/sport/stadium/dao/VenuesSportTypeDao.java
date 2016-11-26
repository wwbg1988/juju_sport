package com.juju.sport.stadium.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.base.mapper.VenuesSportTypeMapper;
import com.juju.sport.base.pojo.VenuesSportType;
import com.juju.sport.base.pojo.VenuesSportTypeExample;
import com.juju.sport.common.mybatis.MyBatisBaseDao;


@Repository
public class VenuesSportTypeDao extends MyBatisBaseDao<VenuesSportType>{
	@Autowired
	@Getter
	private VenuesSportTypeMapper mapper;	
	public List<VenuesSportType> selectSportTypeByVenusInfoId(String id){
		VenuesSportTypeExample example = new VenuesSportTypeExample();
		VenuesSportTypeExample.Criteria criteria = example.createCriteria();
		criteria.andVenuesInfoIdEqualTo(id);   //消息类型为场馆消息
		return mapper.selectByExample(example);
	}
}
