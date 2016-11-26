package com.juju.sport.user.service;

import org.springframework.stereotype.Service;

import com.juju.sport.user.dto.DeviceUsersDto;

@Service
public interface IDeviceUsersService {
	public DeviceUsersDto checkUser(String userNo);
}
