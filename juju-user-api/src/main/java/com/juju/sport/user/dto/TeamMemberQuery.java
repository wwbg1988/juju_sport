package com.juju.sport.user.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class TeamMemberQuery implements Serializable{
	
	@Getter
	@Setter
	public String teamId;

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
	
	
}
