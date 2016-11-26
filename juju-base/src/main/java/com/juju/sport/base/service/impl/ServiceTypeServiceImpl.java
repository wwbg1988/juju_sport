package com.juju.sport.base.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.juju.sport.base.dao.ServiceTypeDao;
import com.juju.sport.base.dto.ServiceTypeDto;
import com.juju.sport.base.pojo.ServiceType;
import com.juju.sport.base.service.IServiceTypeService;
import com.juju.sport.common.util.BeanUtils;

@Service
public class ServiceTypeServiceImpl implements IServiceTypeService{

	@Autowired
	@Getter
	private ServiceTypeDao serviceTypeDao;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	private final static String FINDALLKEYCODE= "com.juju.sports.api.home.serviceType.all";
	
	@Override
	public List<ServiceTypeDto> findBy(ServiceTypeDto serviceTypeDto) {
		Object result = redisTemplate.opsForValue().get(FINDALLKEYCODE);
		if (result != null) {
			return (List<ServiceTypeDto>) result;
		}
		List<ServiceType> list = serviceTypeDao.findBy(serviceTypeDto);
		List<ServiceTypeDto> resultList = null;
		if(!CollectionUtils.isEmpty(list)){
			resultList = BeanUtils.createBeanListByTarget(list, ServiceTypeDto.class);
		}
		redisTemplate.opsForValue().set(FINDALLKEYCODE, resultList, 1, TimeUnit.HOURS);
		return resultList;
	}

}
