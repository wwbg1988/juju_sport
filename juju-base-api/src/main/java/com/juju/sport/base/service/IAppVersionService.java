package com.juju.sport.base.service;

import org.springframework.stereotype.Service;

import com.juju.sport.base.dto.AppVersionDto;

@Service
public interface IAppVersionService {
	public AppVersionDto queryBy(AppVersionDto appVersionDto);
}
