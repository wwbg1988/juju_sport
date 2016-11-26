package com.juju.sport.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.juju.sport.base.dao.HomePageConfigDao;
import com.juju.sport.base.dto.HomePageConfigDto;
import com.juju.sport.base.dto.PageData;
import com.juju.sport.base.pojo.HomePageConfig;
import com.juju.sport.base.service.IHomePageConfigSerivce;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.JsonUtil;
import com.juju.sport.common.util.StringUtils;

@Service
public class HomePageConfigSerivceImpl implements IHomePageConfigSerivce {

	@Autowired
	private HomePageConfigDao configDao;
	
	@Override
	public Map<String, List<PageData>> findAll() {
		List<HomePageConfig> all = configDao.findAll();
		if(CollectionUtils.isEmpty(all)) {
			return null;
		}
		Map<String, List<PageData>> results = new HashMap<String, List<PageData>>();
		for(HomePageConfig config : all) {
			List<PageData> list = createIfAbsent(config.getDataKey(), results);
			PageData data = createPageData(config.getDataValue());
			list.add(data);
		}
		return results;
	}
	
	@Override
	public List<PageData> findByKey(String key) {
		List<HomePageConfig> all = configDao.findByKey(key);
		List<PageData> reuslts = new ArrayList<>();
		for(HomePageConfig config : all) {
			PageData data = createPageData(config.getDataValue());
			data.setImage(process(data.getImage()));;
			data.setId(config.getId());
			reuslts.add(data);
		}
		return reuslts;
	}

	private List<PageData> createIfAbsent(String key, Map<String, List<PageData>> results) {
		List<PageData> list = results.get(key);
		if(CollectionUtils.isEmpty(list)) {
			list = new ArrayList<PageData>();
			results.put(key, list);
		}
		return list;
	}

	private PageData createPageData(String value) {
		if(StringUtils.isEmpty(value)) {
			return null;
		}
		return JsonUtil.getObjFromJson(value, PageData.class);
	}

	@Override
	public void save(HomePageConfigDto configDto) {
		HomePageConfig config = BeanUtils.createBeanByTarget(configDto, HomePageConfig.class); 
		configDao.insert(config);
	}

	@Override
	public RequestResult deleteById(String configId) {
		configDao.logicDeleteById(configId);
		return new RequestResult(true, "删除成功");
	}

	@Override
	public int countAll() {
		return configDao.countAll();
	}
	
	private String process(String image) {
		if(StringUtils.isNotEmpty(image) && image.startsWith("/")) {
			image = image.substring(1);
		}
		return image;
	}

}
