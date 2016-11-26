package com.juju.sport.user.api;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.user.dto.UserQuery;
import com.juju.sport.user.dto.UsersDto;

public interface IUserQueryService {

	PageResult<UsersDto> find(UserQuery query, PageQuery changeToPageQuery);

	
	
}
