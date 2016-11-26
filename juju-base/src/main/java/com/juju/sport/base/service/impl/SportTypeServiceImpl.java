package com.juju.sport.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.juju.sport.base.cache.ISportTypeCache;
import com.juju.sport.base.dao.SportTypeDao;
import com.juju.sport.base.dto.SportTypeDto;
import com.juju.sport.base.pojo.SportType;
import com.juju.sport.base.service.ISportTypeService;
import com.juju.sport.common.dto.Pair;
import com.juju.sport.common.model.ListResult;
import com.juju.sport.common.util.BeanUtils;

@Service
public class SportTypeServiceImpl implements ISportTypeService {

	@Autowired
	private SportTypeDao sportTypeDao;
	
	@Autowired
	private ISportTypeCache sportTypeCache;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	private final static String FINDALLKEYCODE= "com.juju.sports.api.home.sportType.all";
	
	private final static String FINDBYKEYCODE="com.juju.sports.api.home.sportType.by";
	
	@Override
	public List<SportTypeDto> findBy(SportTypeDto sportTypeDto) {
		Object result = redisTemplate.opsForValue().get(FINDBYKEYCODE);
		if(result!=null){
			return (List<SportTypeDto>) result;
		}
		List<SportType> list = sportTypeDao.findBy(sportTypeDto);
		List<SportTypeDto> results = BeanUtils.createBeanListByTarget(list, SportTypeDto.class);
		redisTemplate.opsForValue().set(FINDBYKEYCODE, results, 1, TimeUnit.HOURS);
		return results;
	}

	@Override
	public List<SportTypeDto> findAll() {
		Object result = redisTemplate.opsForValue().get(FINDALLKEYCODE);
		if (result != null) {
			return (List<SportTypeDto>) result;
		}
		List<SportType> list = sportTypeDao.findAll();
		List<SportTypeDto> results = BeanUtils.createBeanListByTarget(list,
				SportTypeDto.class);
		redisTemplate.opsForValue().set(FINDALLKEYCODE, results, 1, TimeUnit.HOURS);
		return results;
	
	}

	@Override
	public ListResult<Pair<String, String>> findAllComboData() {
		List<SportTypeDto> list = sportTypeCache.findAll();
		List<Pair<String, String>> results = new ArrayList<Pair<String, String>>();
		if(CollectionUtils.isEmpty(list)) {
			return new ListResult<Pair<String, String>>(results);
		}
		
		for(SportTypeDto type : list){
			Pair<String, String> pair = new Pair<String, String>(type.getId(), type.getSportName());
			results.add(pair);
		}
		
		return new ListResult<Pair<String,String>>(results);
	}

	@Override
	public List<SportTypeDto> findByStad(String userId) {
		
		return sportTypeDao.findByStad(userId);
	}
	
}
