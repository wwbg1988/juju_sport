package com.juju.sport.home.homepage.test;

import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.juju.sport.admin.home.cache.PageDataCache;
import com.juju.sport.base.dto.PageData;
import com.juju.sport.base.service.IHomePageConfigSerivce;
import com.juju.sport.home.test.BaseTestCase;

public class HomePageConfigSerivceTest extends BaseTestCase {

	@Autowired
	private IHomePageConfigSerivce iHomePageConfigSerivce;
	
	@Autowired
	private PageDataCache cache;
	
	@Test
	public void testFind(){
		Map<String, List<PageData>> result = iHomePageConfigSerivce.findAll();
		System.out.println(result);
		
		//List<PageData> list = iHomePageConfigSerivce.findByKey("hot_sale");
		//System.out.println(list);
	}
	
	@Test
	public void testCache() {
		Map<String, List<PageData>> result = cache.findAll();
		System.out.println(result);
		
		List<PageData> list = cache.findByKey("hot_sale");
		System.out.println(list);
	}
}
