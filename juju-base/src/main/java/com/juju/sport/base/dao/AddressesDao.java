package com.juju.sport.base.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.juju.sport.base.dto.AddressesDto;
import com.juju.sport.base.mapper.AddressesMapper;
import com.juju.sport.base.pojo.Addresses;
import com.juju.sport.base.pojo.AddressesExample;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.StringUtils;

@Repository
public class AddressesDao extends MyBatisBaseDao<Addresses> {

	@Autowired
	@Getter
	private AddressesMapper mapper;

	public List<Addresses> findBy(AddressesDto addressesDto) {
		AddressesExample example = new AddressesExample();
		AddressesExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);

		if(addressesDto.getLevel()!=null){
			criteria.andLevelEqualTo(addressesDto.getLevel());	
		}

		if (!StringUtils.isEmpty(addressesDto.getParentId())) {
			criteria.andParentIdEqualTo(addressesDto.getParentId());
		}
		return mapper.selectByExample(example);
	}

	public Addresses findById(Integer key) {
		AddressesExample example = new AddressesExample();
		AddressesExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		criteria.andIdEqualTo(key);
		List<Addresses> addresses = mapper.selectByExample(example);
		return CollectionUtils.isEmpty(addresses) ? null : addresses.get(0);
	}

	public List<Addresses> findByParentId(String parentId) {
		AddressesExample example = new AddressesExample();
		AddressesExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		criteria.andParentIdEqualTo(parentId);
		return mapper.selectByExample(example);
	}
}
