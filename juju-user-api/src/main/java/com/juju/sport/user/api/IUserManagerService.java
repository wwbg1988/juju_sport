package com.juju.sport.user.api;

import com.juju.sport.common.model.RequestResult;
import com.juju.sport.user.dto.UsersDto;

public interface IUserManagerService {

	RequestResult update(UsersDto user);

	RequestResult deleteById(String id);

}
