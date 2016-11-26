package com.juju.sport.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.juju.sport.base.pojo.Messages;

public interface MessageExMapper {
	List<Messages> selectSysMessages(@Param("starNum")Long starNum,@Param("pageSize") int pageSize);
	List<Messages> selectStadiumMessages(@Param("starNum")Long starNum,@Param("pageSize") int pageSize);
}