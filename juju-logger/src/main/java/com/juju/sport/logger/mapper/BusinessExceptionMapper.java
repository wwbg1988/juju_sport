package com.juju.sport.logger.mapper;

import org.apache.ibatis.annotations.Param;

import com.juju.sport.common.jdbc.DataSource;
import com.juju.sport.logger.dto.BusinessExceptionDto;

public interface BusinessExceptionMapper {

	@DataSource("logger")
	int insert(@Param("exception")BusinessExceptionDto exception, @Param("tableName")String tableName);

}
