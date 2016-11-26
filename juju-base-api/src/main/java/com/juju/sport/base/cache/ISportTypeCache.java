package com.juju.sport.base.cache;

import java.util.List;

import com.juju.sport.base.dto.SportTypeDto;

public interface ISportTypeCache {

	public List<SportTypeDto> findAll();

	public String findNameByID(String id);
	
	public String getNameStr(String idStr);
}
