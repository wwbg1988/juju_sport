package com.juju.sport.base.cache.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.base.cache.ISportTypeCache;
import com.juju.sport.base.dao.SportTypeDao;
import com.juju.sport.base.dto.SportTypeDto;
import com.juju.sport.base.pojo.SportType;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.StringUtils;

@Service
public class SportTypeCacheImpl implements ISportTypeCache{
	
	private static Map<String, SportTypeDto> allSport;

	@Autowired
	private SportTypeDao sportTypeDao;
	
	
	public List<SportTypeDto> findAll() {
		if(allSport == null) {
			init();
		}
		List<SportTypeDto> results = new ArrayList<SportTypeDto>();
		for(SportTypeDto sportDto : allSport.values()) {
			results.add(sportDto);
		}
		return results;
	}

	public String findNameByID(String id) {
		if(allSport == null) {
			init();
		}
		SportTypeDto sport = allSport.get(id);
		return sport != null ? sport.getSportName() : "";
	}

	public String getNameStr(String idStr){
		if(StringUtils.isEmpty(idStr)) {
			return "";
		}
		String[] ids = StringUtils.split(idStr, ",");
		
		StringBuilder nameStrs = new StringBuilder();
		int i = 0;
		for(String id : ids) {
			if(i == 0) {
				nameStrs.append(this.findNameByID(id));
			}else{
				nameStrs.append(",").append(this.findNameByID(id));
			}
			i++;
		}
		return nameStrs.toString();
	}
	
	private void init() {
		allSport = new LinkedHashMap<String, SportTypeDto>();
		List<SportType> all = sportTypeDao.findAll();
		for(SportType sport : all) {
			allSport.put(sport.getId(), (SportTypeDto)BeanUtils.createBeanByTarget(sport, SportTypeDto.class));
		}
	}
	
	
}
