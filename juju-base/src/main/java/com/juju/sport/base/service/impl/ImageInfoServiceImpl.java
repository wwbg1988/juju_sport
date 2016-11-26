package com.juju.sport.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.base.dao.ImageInfoDao;
import com.juju.sport.base.dto.ImageInfoDto;
import com.juju.sport.base.pojo.ImageInfo;
import com.juju.sport.base.service.IImageInfoService;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.model.RequestResult;
import com.juju.sport.common.util.BeanUtils;

@Service
public class ImageInfoServiceImpl implements IImageInfoService {

	@Autowired
	private ImageInfoDao imageInfoDao;
	
	@Override
	public PageResult<ImageInfoDto> findByPage(String name, PageQuery page) {
		List<ImageInfo> imageInfos = imageInfoDao.findByPage(name, page);
		List<ImageInfoDto> imageInfoDto = BeanUtils.createBeanListByTarget(imageInfos, ImageInfoDto.class);
		long total = imageInfoDao.count(name);		
		page.setTotal(total);
		return new PageResult<ImageInfoDto>(page, imageInfoDto);
	}

	@Override
	public void create(ImageInfoDto imageInfo) {
		ImageInfo image = BeanUtils.createBeanByTarget(imageInfo, ImageInfo.class);
		imageInfoDao.insert(image);
	}

	@Override
	public RequestResult logicDeleteById(String id) {
		imageInfoDao.logicDeleteById(id);
		return new RequestResult(true, "删除成功");
	}

}
