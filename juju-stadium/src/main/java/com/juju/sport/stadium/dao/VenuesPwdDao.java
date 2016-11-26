package com.juju.sport.stadium.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.stadium.mapper.VenuesPwdMapper;
import com.juju.sport.stadium.pojo.VenuesPwd;
import com.juju.sport.stadium.pojo.VenuesPwdExample;

@Repository
public class VenuesPwdDao extends MyBatisBaseDao<VenuesPwd> {
    @Getter
	@Autowired
	private VenuesPwdMapper mapper;

	public List<VenuesPwd> findVenuesPwd(String sid) {

	    VenuesPwdExample example = new VenuesPwdExample();
	    VenuesPwdExample.Criteria criteria= example.createCriteria();
	    criteria.andSidEqualTo(sid);
	    return mapper.selectByExample(example);

	}
	
}
