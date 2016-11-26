package com.juju.sport.game.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.game.dto.RaceInfoDto;
import com.juju.sport.game.dto.RaceInfoQuery;

public interface RaceInfoExMapper {

	List<RaceInfoDto> findByPage(@Param("query")RaceInfoQuery query, @Param("page")PageQuery page);

	
}
