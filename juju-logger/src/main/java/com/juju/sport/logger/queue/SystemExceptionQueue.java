package com.juju.sport.logger.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.common.redis.WdRedisDao;
import com.juju.sport.common.support.Queue;
import com.juju.sport.logger.dto.SystemExceptionDto;

@Service
public class SystemExceptionQueue implements Queue<SystemExceptionDto> {
	
	@Autowired
	private WdRedisDao<SystemExceptionDto> systemExceptionDao;

	@Override
	public void push(SystemExceptionDto t) {
		systemExceptionDao.setToList(SystemExceptionDto.REDIS_KEY, t);
	}

	@Override
	public SystemExceptionDto pop() {
		return systemExceptionDao.pop(SystemExceptionDto.REDIS_KEY);
	}

	@Override
	public void empty() {
		systemExceptionDao.delete(SystemExceptionDto.REDIS_KEY);
	}

	@Override
	public SystemExceptionDto bPop() {
		return systemExceptionDao.bpop(SystemExceptionDto.REDIS_KEY, 10);
	}

}
