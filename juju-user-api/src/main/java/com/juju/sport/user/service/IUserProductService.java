package com.juju.sport.user.service;

import org.springframework.stereotype.Service;

import com.juju.sport.user.dto.UserProductDto;

/**
 * 
	 * 此类描述的是：用户已经兑换了的积分信息
	 * @author: cwftalus@163.com
	 * @version: 2015年4月16日 下午3:59:24
 */
@Service
public interface IUserProductService {
	public String saveOrUpdate(UserProductDto userProductDto);
}
