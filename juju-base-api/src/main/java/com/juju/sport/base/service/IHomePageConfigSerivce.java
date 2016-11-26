package com.juju.sport.base.service;

import java.util.List;
import java.util.Map;

import com.juju.sport.base.dto.HomePageConfigDto;
import com.juju.sport.base.dto.PageData;
import com.juju.sport.common.model.RequestResult;

public interface IHomePageConfigSerivce {

	Map<String, List<PageData>> findAll();
	
	List<PageData> findByKey(String key);

	void save(HomePageConfigDto config);

	RequestResult deleteById(String configId);

	int countAll();

}
