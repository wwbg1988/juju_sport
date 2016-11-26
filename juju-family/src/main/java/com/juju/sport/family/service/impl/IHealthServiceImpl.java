package com.juju.sport.family.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juju.sport.family.dao.HealthDao;
import com.juju.sport.family.dto.HealthDto;
import com.juju.sport.family.pojo.Health;
import com.juju.sport.family.service.IHealthService;


@Service
public class IHealthServiceImpl implements IHealthService{

	@Autowired
	public HealthDao hdao;
	
	@Override
	public HealthDto findAll() {
		// TODO Auto-generated method stub
		Health health = hdao.getHealth();
		HealthDto hdto = new HealthDto();
		BeanUtils.copyProperties(health,hdto);
		return hdto;
	}

}
