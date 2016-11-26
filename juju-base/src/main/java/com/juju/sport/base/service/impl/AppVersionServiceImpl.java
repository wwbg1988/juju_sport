package com.juju.sport.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.base.dao.AppVersionDao;
import com.juju.sport.base.dto.AppVersionDto;
import com.juju.sport.base.pojo.AppVersion;
import com.juju.sport.base.service.IAppVersionService;
import com.juju.sport.common.util.BeanUtils;

@Service
public class AppVersionServiceImpl implements IAppVersionService {

	@Autowired
	private AppVersionDao appVersionDao;
	
	@Override
	public AppVersionDto queryBy(AppVersionDto appVersionDto) {
		List<AppVersion> dataList = appVersionDao.queryBy(appVersionDto);
		List<AppVersionDto> resultList = BeanUtils.createBeanListByTarget(dataList, AppVersionDto.class);
		if(resultList!=null && resultList.size()>0){
			return resultList.get(0);
		}
		return null;
	}

}
