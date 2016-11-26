package com.juju.sport.family.service;

import org.springframework.stereotype.Service;

import com.juju.sport.family.dto.SpecificationsDto;

@Service
public interface ISpecificationsService {
	public SpecificationsDto findByPro(String sex,int age);
}
