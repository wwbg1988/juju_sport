package com.juju.sport.logger.dao.test;

import com.juju.sport.common.util.UUIDGenerator;
import com.juju.sport.logger.dao.BusinessExceptionDao;
import com.juju.sport.logger.dto.BusinessExceptionDto;
import com.juju.sport.logger.test.BaseTestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by rkzhang on 14-9-20.
 */
public class BusinessExceptionDaoTest extends BaseTestCase{

    @Autowired
    private BusinessExceptionDao businessExceptionDao;

    @Test
    public void testInsertException(){
        BusinessExceptionDto exception = new BusinessExceptionDto();
        exception.setId(UUIDGenerator.getUUID());
        exception.setCreateTime(new Date());
        exception.setErrorCode("001");
        exception.setErrorMessage("test messsage");
        exception.setErrorStackTrace("stack trace");
        exception.setLoginUserAccount("login user account");
        exception.setRequestParams("request params");
        exception.setRequestUrl("request url");

        businessExceptionDao.saveException(exception);
    }
}
