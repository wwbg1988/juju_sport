package com.juju.sport.base.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.base.dto.AppVersionDto;
import com.juju.sport.base.mapper.AppVersionMapper;
import com.juju.sport.base.pojo.AppVersion;
import com.juju.sport.base.pojo.AppVersionExample;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.mybatis.MyBatisBaseDao;

@Repository
public class AppVersionDao extends MyBatisBaseDao<AppVersion> {
	@Autowired
	@Getter
	private AppVersionMapper mapper;

	public List<AppVersion> queryBy(AppVersionDto appVersionDto) {
		AppVersionExample example = new AppVersionExample();
		AppVersionExample.Criteria criteria = example.createCriteria();
		criteria.andVersionTypeEqualTo(appVersionDto.getVersionType());
		criteria.andVersionNumLessThan(appVersionDto.getVersionNum());
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByExample(example);
	}

}
