package com.juju.sport.admin.manager.dto.test;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.juju.sport.common.redis.RedisKeyPrefix;

@ToString
@RedisKeyPrefix(prefixValue="test:TestDto:userName")
public class TestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2480755996930439035L;

	@Getter
	@Setter
	private String userName;
	
	@Getter
	@Setter
	private String password;

}