package com.juju.sport.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.juju.sport.base.dto.SportTypeDto;

public interface SportTypeExMapper {
	List<SportTypeDto> findByStad(@Param("userId") String userId);
}