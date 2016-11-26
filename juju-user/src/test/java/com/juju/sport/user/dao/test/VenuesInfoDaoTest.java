package com.juju.sport.user.dao.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.juju.sport.user.dao.VenuesInfoDao;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.pojo.VenuesInfo;
import com.juju.sport.user.test.BaseTestCase;

public class VenuesInfoDaoTest extends BaseTestCase {

	protected static final Log logger = LogFactory.getLog(VenuesInfoDaoTest.class);
	
	@Autowired
	private VenuesInfoDao venuesInfoDao;
	
	@Test
	public void  findTest() {
//		VenuesInfo info = venuesInfoDao.selectByPrimaryKey("2cdbf147-48d6-4f3b-8722-a58a78f0ac23");
//		logger.info(info);
		VenusInfoDto venusInfoDto = new VenusInfoDto();
		venusInfoDto.setOtherServices("1");
		venusInfoDto.setVenueType("2");
		int count  = venuesInfoDao.findVenusCount(venusInfoDto);
		logger.info(count);
		
	}
}
