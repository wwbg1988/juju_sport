package com.juju.sport.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.user.dto.UserQuery;
import com.juju.sport.user.dto.UsersDto;

public interface UserExMapper {

	List<UsersDto> findByPage(@Param("query")UserQuery query, @Param("page")PageQuery page);

	long count(@Param("query")UserQuery query);
	
}
