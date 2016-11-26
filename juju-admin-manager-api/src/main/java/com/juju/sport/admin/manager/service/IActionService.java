package com.juju.sport.admin.manager.service;

import org.springframework.stereotype.Service;

import com.juju.sport.admin.manger.dto.ActionDto;
import com.juju.sport.common.model.ListResult;
import com.juju.sport.common.model.RequestResult;

@Service
public interface IActionService {

	ListResult<ActionDto> findAll();

	RequestResult saveOrUpdate(ActionDto action);

	RequestResult deleteAction(String actionId);

}
