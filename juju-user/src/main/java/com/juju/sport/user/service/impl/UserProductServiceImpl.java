package com.juju.sport.user.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.UUIDGenerator;
import com.juju.sport.user.dao.UserProductDao;
import com.juju.sport.user.dto.UserProductDto;
import com.juju.sport.user.pojo.TeamList;
import com.juju.sport.user.pojo.UserProduct;
import com.juju.sport.user.service.IUserProductService;

/**
 * 
 * 此类描述的是：
 * 
 * @author: cwftalus@163.com
 * @version: 2015年4月16日 下午4:12:44
 */
@Service
public class UserProductServiceImpl implements IUserProductService {

	@Autowired
	private UserProductDao userProductDao;

	@Override
	public String saveOrUpdate(UserProductDto userProductDto) {
		UserProduct userProduct = BeanUtils.createBeanByTarget(userProductDto,
				UserProduct.class);
		if (StringUtils.isEmpty(userProductDto.getId())) {
			String userProductId = UUIDGenerator.getUUID();
			userProduct.setId(userProductId);// 订单Id
			userProductDao.insert(userProduct);
		} else {
			userProductDao.updateByPrimaryKey(userProduct);
		}
		return "0";
	}

}
