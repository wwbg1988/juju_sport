package com.juju.sport.user.service;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.user.dto.MedalDto;

public interface IMedalManagerService {

	PageResult<MedalDto> findAll(PageQuery changeToPageQuery);

	void createMedal(MedalDto medal);

	RequestResult deleteMedal(String medalId);

}
