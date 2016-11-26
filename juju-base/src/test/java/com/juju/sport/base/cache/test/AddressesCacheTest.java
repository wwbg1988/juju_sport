package com.juju.sport.base.cache.test;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.juju.sport.base.cache.IAddressesCache;
import com.juju.sport.base.dto.AddressesDto;
import com.juju.sport.base.test.BaseTestCase;

public class AddressesCacheTest extends BaseTestCase {
	
	protected static final Log logger = LogFactory.getLog(AddressesCacheTest.class);

	@Autowired
	private IAddressesCache addressesCache;
	
	@Test
	public void cacheTest() {
		String name = addressesCache.findNameById(110000);
		logger.info(name);
		
		List<AddressesDto> list = addressesCache.findByParentId("110100");
		for(AddressesDto address : list) {
			logger.info(address);
		}
	}
}
