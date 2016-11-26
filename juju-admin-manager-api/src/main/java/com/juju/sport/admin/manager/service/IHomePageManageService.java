package com.juju.sport.admin.manager.service;

import com.juju.sport.common.model.RequestResult;

public interface IHomePageManageService {

	RequestResult addVenuesToHomePage(String venuesId, String pageKey);

}
