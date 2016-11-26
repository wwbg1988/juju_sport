package com.juju.sport.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.base.cache.IAddressesCache;
import com.juju.sport.base.dao.AddressesDao;
import com.juju.sport.base.dto.AddressesDto;
import com.juju.sport.base.pojo.Addresses;
import com.juju.sport.base.service.IAddressesService;
import com.juju.sport.common.model.ListResult;
import com.juju.sport.common.util.BeanUtils;

@Service
public class AddressesServiceImpl implements IAddressesService {

	@Autowired
	private AddressesDao addressesDao;
	
	@Autowired
	private IAddressesCache addressesCache;
	
	@Override
	public List<AddressesDto> findBy(AddressesDto addressesDto) {
		List<Addresses> list = addressesDao.findBy(addressesDto);
		List<AddressesDto> results = BeanUtils.createBeanListByTarget(list, AddressesDto.class);
		return results;
	}

	@Override
	public ListResult<AddressesDto> findByParentId(Integer parentId) {
		List<AddressesDto> lists = addressesCache.findByParentId(parentId.toString());
		return new ListResult<AddressesDto>(lists);
	}

}
