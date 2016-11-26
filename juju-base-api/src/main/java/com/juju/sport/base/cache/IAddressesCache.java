package com.juju.sport.base.cache;

import java.util.List;

import com.juju.sport.base.dto.AddressesDto;

public interface IAddressesCache {

	public AddressesDto get(Integer key);
	
	public String findNameById(Integer key);
	
	public List<AddressesDto> findByParentId(String parentId);
}
