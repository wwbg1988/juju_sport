package com.juju.sport.logger.mapper;

import org.apache.ibatis.annotations.Param;

import com.juju.sport.common.jdbc.DataSource;
import com.juju.sport.logger.dto.SystemExceptionDto;

public interface SystemExceptionMapper {

	@DataSource("logger")
	int insert(@Param("exception")SystemExceptionDto exception, @Param("tableName")String tableName);
}
