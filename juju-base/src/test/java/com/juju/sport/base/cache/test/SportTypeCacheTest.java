package com.juju.sport.base.cache.test;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.juju.sport.base.cache.ISportTypeCache;
import com.juju.sport.base.dto.SportTypeDto;
import com.juju.sport.base.test.BaseTestCase;

public class SportTypeCacheTest extends BaseTestCase {

	protected static final Log logger = LogFactory.getLog(AddressesCacheTest.class);
	
	@Autowired
	private ISportTypeCache sportTypeCache;
	
	@Test
	public void cacheTest() {
		logger.info(sportTypeCache.findNameByID("1"));
		
		List<SportTypeDto> sports = sportTypeCache.findAll();
		for(SportTypeDto sport : sports){
			logger.info(sport);
		}	
	}
}
