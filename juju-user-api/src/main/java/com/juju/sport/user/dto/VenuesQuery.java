package com.juju.sport.user.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class VenuesQuery implements Serializable{

	@Getter
	@Setter
	private String userAccount;
	
	@Getter
	@Setter
	private String nickName;
	
	@Getter
	@Setter
	private Integer provinceId;
	
	@Getter
	@Setter
	private Integer cityId;
	
	@Getter
	@Setter
	private Integer countryId;
	
	@Getter
	@Setter
	private Integer stat;
	
}
