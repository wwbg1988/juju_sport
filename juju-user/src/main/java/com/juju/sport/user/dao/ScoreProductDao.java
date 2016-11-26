package com.juju.sport.user.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.user.dto.ScoreProductDto;
import com.juju.sport.user.mapper.ScoreProductMapper;
import com.juju.sport.user.pojo.ScoreProduct;
import com.juju.sport.user.pojo.ScoreProductExample;
import com.juju.sport.user.pojo.ScoreProductExample.Criteria;


/**
 * 
	 * 此类描述的是：查询用户积分兑换礼品
	 * @author: cwftalus@163.com
	 * @version: 2015年4月9日 下午3:12:11
 */
@Repository
public class ScoreProductDao extends MyBatisBaseDao<ScoreProduct>{
	
    @Autowired
    @Getter
    private ScoreProductMapper mapper;

	public List<ScoreProduct> findBy(ScoreProductDto scoreProductDto,
			PageQuery page) {
		ScoreProductExample example = new ScoreProductExample();
		example = queryBySql(scoreProductDto);
		example.setOrderByClause(scoreProductDto.getOrderColumn()+" "+scoreProductDto.getOrderBy()+" limit " + page.getStartNum() + ", " + page.getPageSize());
		List<ScoreProduct> list = mapper.selectByExample(example);
		return list;
	}

	public long count(ScoreProductDto scoreProductDto) {
		ScoreProductExample example = new ScoreProductExample();
		example = queryBySql(scoreProductDto);
		return mapper.countByExample(example);
	}
	
	public ScoreProductExample queryBySql(ScoreProductDto scoreProductDto){
		ScoreProductExample example = new ScoreProductExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		
		//Id进行
		if(!StringUtils.isEmpty(scoreProductDto.getId())){
			criteria.andIdEqualTo(scoreProductDto.getId());	
		}
		
		return example;
	}

	public List<ScoreProduct> findBy(ScoreProductDto scoreProductDto) {
		ScoreProductExample example = new ScoreProductExample();
		example = queryBySql(scoreProductDto);
		List<ScoreProduct> list = mapper.selectByExample(example);
		return list;
	}
}
