package com.juju.sport.user.service;

import org.springframework.stereotype.Service;

import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.user.dto.ScoreProductDto;

/**
 * 
 * 此类描述的是：描述用户所属积分可兑换的礼品信息
 * 
 * @author: cwftalus@163.com
 * @version: 2015年4月9日 下午1:19:59
 */
@Service
public interface IScoreProductService {
	public PageResult<ScoreProductDto> findBy(ScoreProductDto scoreProductDto,PageQuery page);

	public ScoreProductDto findBy(ScoreProductDto scoreProductDto);
}
