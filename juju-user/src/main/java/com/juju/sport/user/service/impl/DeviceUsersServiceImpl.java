package com.juju.sport.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.user.dao.DeviceUsersDao;
import com.juju.sport.user.dto.DeviceUsersDto;
import com.juju.sport.user.pojo.DeviceUsers;
import com.juju.sport.user.service.IDeviceUsersService;

@Service
public class DeviceUsersServiceImpl implements IDeviceUsersService {

	@Autowired
	private DeviceUsersDao deviceUsersDao;
	
	@Override
	public DeviceUsersDto checkUser(String userNo) {
		DeviceUsers  deviceUsers =  deviceUsersDao.checkUser(userNo);
		DeviceUsersDto deviceUsersDto = null;
		if(deviceUsers!=null){
			deviceUsersDto = BeanUtils.createBeanByTarget(deviceUsers, DeviceUsersDto.class);	
		}
		return deviceUsersDto;
	}

}
