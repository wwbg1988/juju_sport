package com.juju.sport.user.dao;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.user.mapper.DeviceUsersMapper;
import com.juju.sport.user.pojo.DeviceUsers;
import com.juju.sport.user.pojo.DeviceUsersExample;
import com.juju.sport.user.pojo.DeviceUsersExample.Criteria;

@Repository
public class DeviceUsersDao extends MyBatisBaseDao<DeviceUsers>{
	
    @Autowired
    @Getter
    private DeviceUsersMapper mapper;

	public DeviceUsers checkUser(String userNo) {
		DeviceUsersExample example = new DeviceUsersExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo(userNo);
		List<DeviceUsers> resultList = new ArrayList<DeviceUsers>();

		resultList = mapper.selectByExample(example); 
		if(resultList!=null && resultList.size()>0){
			return resultList.get(0);	
		}else{
			return null;
		}
	}
}
