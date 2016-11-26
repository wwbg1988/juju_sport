package com.juju.sport.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.user.dao.MedalDao;
import com.juju.sport.user.dto.MedalDto;
import com.juju.sport.user.pojo.Medal;
import com.juju.sport.user.service.IMedalManagerService;

@Service
public class MedalManagerServiceImpl implements IMedalManagerService {
	
	@Autowired
	private MedalDao medalDao;
	
	@Override
	public PageResult<MedalDto> findAll(PageQuery page) {
		List<Medal> results = medalDao.findAll(page);
		int total = medalDao.countAll();
		page.setTotal(total);
		List<MedalDto> dtos = BeanUtils.createBeanListByTarget(results, MedalDto.class);
		for(MedalDto dto : dtos) {
			String typeName = MedalDto.MedalType.getDescription(dto.getType());
			dto.setTypeName(typeName);
		}
		return new PageResult<MedalDto>(page, dtos);
	}

	@Override
	public void createMedal(MedalDto medal) {
		Medal result = BeanUtils.createBeanByTarget(medal, Medal.class);
		medalDao.insert(result);
	}

	@Override
	public RequestResult deleteMedal(String medalId) {
		medalDao.logicDeleteById(medalId);
		return new RequestResult(true, "删除成功!");
	}

}
