package com.juju.sport.user.service;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.user.dto.VenuesQuery;
import com.juju.sport.user.dto.VenusInfoDto;

public interface IVenuesInfoService {

	PageResult<VenusInfoDto> find(VenuesQuery query, PageQuery changeToPageQuery);

	RequestResult update(VenusInfoDto user);

	RequestResult deleteById(String id);
	
	VenusInfoDto findByID(String id);

	String updateSpaceNum(String userId,boolean flag);

}
