package com.juju.sport.user.dao;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.user.mapper.UserProductMapper;
import com.juju.sport.user.pojo.UserProduct;

/**
 * 
	 * 此类描述的是：
	 * @author: cwftalus@163.com
	 * @version: 2015年4月16日 下午4:12:40
 */
@Repository
public class UserProductDao extends MyBatisBaseDao<UserProduct>{
	
    @Autowired
    @Getter
    private UserProductMapper mapper;
}
