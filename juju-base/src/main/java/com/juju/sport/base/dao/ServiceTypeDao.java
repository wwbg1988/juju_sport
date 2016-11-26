package com.juju.sport.base.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.juju.sport.base.dto.ServiceTypeDto;
import com.juju.sport.base.mapper.ServiceTypeMapper;
import com.juju.sport.base.pojo.Addresses;
import com.juju.sport.base.pojo.ServiceType;
import com.juju.sport.base.pojo.ServiceTypeExample;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.mybatis.MyBatisBaseDao;

@Repository
public class ServiceTypeDao  extends MyBatisBaseDao<ServiceType> {
	
		@Autowired
		@Getter
		private ServiceTypeMapper serviceTypeMapper;
	
	public List<ServiceType> findBy(ServiceTypeDto serviceTypeDto){
		ServiceTypeExample example = new ServiceTypeExample();
		ServiceTypeExample.Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(serviceTypeDto.getServiceName())){
			criteria.andServiceNameEqualTo(serviceTypeDto.getServiceName());
		}
		if(!StringUtils.isEmpty(serviceTypeDto.getId())){
			criteria.andIdEqualTo(serviceTypeDto.getId());
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return serviceTypeMapper.selectByExample(example);
	}

	@Override
	public Object getMapper() {
		return serviceTypeMapper;
	}
}
