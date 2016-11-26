package com.juju.sport.sync.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.juju.sport.sync.model.DataInfo;
import com.juju.sport.sync.pojo.DataSync;

public interface DataInfoMapper {

	List<Map<String, Object>> findByDBSync(DataSync sync);
	
	int update(@Param("sql")String sql);
	
	int save(@Param("sql")String sql);
	
	int delete(DataInfo dataInfo);
	
	int count(DataInfo dataInfo);
	
}
