package com.juju.sport.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.juju.sport.base.dto.AddressesDto;
import com.juju.sport.common.model.ListResult;

@Service
public interface IAddressesService {
	
	public List<AddressesDto> findBy(AddressesDto addressesDto);

	public ListResult<AddressesDto> findByParentId(Integer parentId);
}
