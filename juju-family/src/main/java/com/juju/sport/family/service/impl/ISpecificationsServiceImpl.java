package com.juju.sport.family.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.family.dao.SpecificationsDao;
import com.juju.sport.family.dto.SpecificationsDto;
import com.juju.sport.family.pojo.Specifications;
import com.juju.sport.family.service.ISpecificationsService;


@Service
public class ISpecificationsServiceImpl implements ISpecificationsService {

	@Autowired
	public SpecificationsDao sdao;
	
	@Override
	public SpecificationsDto findByPro(String sex, int age) {
		// TODO Auto-generated method stub
		Specifications specifications=sdao.getSpecifications(sex, age);
		SpecificationsDto sdto = new SpecificationsDto();
		if(specifications!=null){
			BeanUtils.copyProperties(specifications,sdto);
			return sdto;
		}
		return null;
	}

}
