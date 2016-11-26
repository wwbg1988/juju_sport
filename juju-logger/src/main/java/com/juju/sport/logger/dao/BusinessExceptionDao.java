package com.juju.sport.logger.dao;

import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.logger.dto.BusinessExceptionDto;
import com.juju.sport.logger.mapper.BusinessExceptionMapper;
import com.juju.sport.logger.util.SysLoggerUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.Getter;

/**
 * Created by rkzhang on 14-9-20.
 */
@Repository
public class BusinessExceptionDao extends MyBatisBaseDao<BusinessExceptionDto>{
 
    @Getter
    @Autowired
    private BusinessExceptionMapper mapper;

    public int saveException(BusinessExceptionDto exception){    	
        return mapper.insert(exception, SysLoggerUtil.getTodayBusinessExceptionLoggerTableName());
    }

   
}
