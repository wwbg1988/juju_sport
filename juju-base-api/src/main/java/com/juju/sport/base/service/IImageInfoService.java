package com.juju.sport.base.service;

import com.juju.sport.base.dto.ImageInfoDto;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;

public interface IImageInfoService {

	PageResult<ImageInfoDto> findByPage(String name, PageQuery page);

	void create(ImageInfoDto image);

	RequestResult logicDeleteById(String id);
}
