package com.juju.sport.stadium.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.juju.sport.base.dto.ServiceTypeDto;
import com.juju.sport.base.dto.SportTypeDto;

public interface VenuesSportExMapper {
//	VenuesInfo selectVenusSportInfo(@Param("id") String id);
	List<SportTypeDto> selectVenusSportInfo(@Param("id") String id);
	List<ServiceTypeDto> selectVenusServiceInfo(@Param("id") String id);
}