package com.juju.sport.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.juju.sport.base.dto.SportTypeDto;
import com.juju.sport.common.dto.Pair;
import com.juju.sport.common.model.ListResult;

@Service
public interface ISportTypeService {
	public List<SportTypeDto> findBy(SportTypeDto sportTypeDto);
	
	public List<SportTypeDto> findAll();
	
	public ListResult<Pair<String, String>> findAllComboData();
	
	public List<SportTypeDto> findByStad(String userId);
}
