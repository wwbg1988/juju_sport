package com.juju.sport.stadium.mapper;

import java.util.List;
import java.util.Map;

import com.juju.sport.common.dto.ParameterDto;
import com.juju.sport.stadium.pojo.SpaceOpenTime;


public interface OwnSpacesOpenTimeMapper {

	List<SpaceOpenTime> querySpaceTimePrice(List<ParameterDto> parameterList);

	List<Map> queryOpenSpaceList(String userAccountId);

}
