package com.juju.sport.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.juju.sport.user.pojo.Team;

public interface TeamMappers {
	List<Team> findJoinList(@Param("id")String  id);

}
