package com.juju.sport.logger.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.common.redis.WdRedisDao;
import com.juju.sport.common.support.Queue;
import com.juju.sport.logger.dto.BusinessExceptionDto;

@Service
public class BusinessExceptionQueue implements Queue<BusinessExceptionDto> {

	@Autowired
	private WdRedisDao<BusinessExceptionDto> businessExceptionDao;
	
	@Override
	public void push(BusinessExceptionDto t) {
		businessExceptionDao.setToList(BusinessExceptionDto.REDIS_KEY, t);
	}

	@Override
	public BusinessExceptionDto pop() {		
		return businessExceptionDao.pop(BusinessExceptionDto.REDIS_KEY);
	}

	@Override
	public void empty() {
		businessExceptionDao.delete(BusinessExceptionDto.REDIS_KEY);
	}

	@Override
	public BusinessExceptionDto bPop() {
		return businessExceptionDao.bpop(BusinessExceptionDto.REDIS_KEY, 10);
	}

}
