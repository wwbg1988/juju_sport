package com.juju.sport.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.user.dao.ScoreProductDao;
import com.juju.sport.user.dto.ScoreProductDto;
import com.juju.sport.user.pojo.ScoreProduct;
import com.juju.sport.user.service.IScoreProductService;

/**
 * 
 * 此类描述的是：接口对应实现类
 * 
 * @author: cwftalus@163.com
 * @version: 2015年4月9日 下午1:33:03
 */
@Service
public class ScoreProductServiceImpl implements IScoreProductService {

	@Autowired
	private ScoreProductDao scoreProductDao;
	
	@Override
	public PageResult<ScoreProductDto> findBy(ScoreProductDto scoreProductDto,PageQuery page) {
		List<ScoreProduct> results = scoreProductDao.findBy(scoreProductDto, page);
		List<ScoreProductDto> productList = BeanUtils.createBeanListByTarget(results,ScoreProductDto.class);
		long total = scoreProductDao.count(scoreProductDto);
		page.setTotal(total);
		return new PageResult<ScoreProductDto>(page, productList);
	}

	@Override
	public ScoreProductDto findBy(ScoreProductDto scoreProductDto) {
		List<ScoreProduct> results = scoreProductDao.findBy(scoreProductDto);
		List<ScoreProductDto> productList = BeanUtils.createBeanListByTarget(results,ScoreProductDto.class);
		if(!productList.isEmpty()){
			return productList.get(0);
		}else{
			return null;
		}
	}

}
