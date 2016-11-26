package com.juju.sport.base.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.base.mapper.ImageInfoMapper;
import com.juju.sport.base.pojo.ImageInfo;
import com.juju.sport.base.pojo.ImageInfoExample;
import com.juju.sport.base.pojo.ImageInfoExample.Criteria;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.StringUtils;

@Repository
public class ImageInfoDao extends MyBatisBaseDao<ImageInfo> {

	@Autowired
	@Getter
	private ImageInfoMapper mapper;
	
	public  List<ImageInfo> findByPage(String name, PageQuery page) {
		ImageInfoExample example = new ImageInfoExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotEmpty(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause(" create_time desc limit " + page.getStartNum() + ", " + page.getPageSize());
		return mapper.selectByExample(example);
	}
	
	public long count(String name) {
		ImageInfoExample example = new ImageInfoExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotEmpty(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}

	public void logicDeleteById(String id) {
		ImageInfo imageInfo = new ImageInfo();
		imageInfo.setId(id);
		imageInfo.setStat(DataStatus.DISABLED);
		mapper.updateByPrimaryKeySelective(imageInfo);
	}
	
}
