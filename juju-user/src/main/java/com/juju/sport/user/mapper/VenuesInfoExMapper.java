package com.juju.sport.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.juju.sport.base.dto.ServiceTypeDto;
import com.juju.sport.base.dto.SportTypeDto;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.user.dto.VenuesQuery;
import com.juju.sport.user.dto.VenusInfoDto;
import com.juju.sport.user.pojo.VenuesInfo;


public interface VenuesInfoExMapper {
	
    Integer findCount(@Param("venusInfoDto") VenusInfoDto venusInfoDto);
    
    List<VenuesInfo> findNameBySportType(@Param("venusInfoDto") VenusInfoDto venusInfoDto);
    
    List<VenuesInfo> findVenusInfo(@Param("venusInfoDto") VenusInfoDto venusInfoDto);
    
    List<SportTypeDto> findVenusSportType(@Param("VenusInfoId")String VenusInfoId);
    
    List<ServiceTypeDto> findVenusServiceType(@Param("VenusInfoId")String VenusInfoId);
    
	List<VenusInfoDto> findByPage(@Param("query")VenuesQuery query, @Param("page")PageQuery page);
	
	Long count(@Param("query")VenuesQuery query);
	
	/**
	 * 返回所有场馆数据信息 
	 * */
	List<VenuesInfo> findVenusByLocation();

}