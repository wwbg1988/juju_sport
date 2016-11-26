package com.juju.sport.family.service;



import org.springframework.stereotype.Service;

import com.juju.sport.family.dto.HealthDto;

@Service
public interface IHealthService {
	public HealthDto findAll();
}
