package com.juju.sport.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.juju.sport.base.dto.ServiceTypeDto;

@Service
public interface IServiceTypeService {
	public List<ServiceTypeDto> findBy(ServiceTypeDto serviceTypeDto);
}
