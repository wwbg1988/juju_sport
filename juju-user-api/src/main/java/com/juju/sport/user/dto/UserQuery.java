package com.juju.sport.user.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class UserQuery implements Serializable{

	@Getter
	@Setter
	public String realName;
	
	@Getter
	@Setter
	public String userAccount;
	
	@Getter
	@Setter
	public Integer provinceId;
	
	@Getter
	@Setter
	public Integer cityId;
	
	@Getter
	@Setter
	private Integer countryId;
	
	@Getter
	@Setter
	private Integer stat;
	
}
