package com.juju.sport.user.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.user.dto.TeamDto;
import com.juju.sport.user.dto.TeamQuery;

public interface TeamExMapper {

	List<TeamDto> findTeamByPage(@Param("query")TeamQuery query, @Param("page")PageQuery page);

	long countTeam(@Param("query")TeamQuery query);
}
