package com.juju.sport.admin.manger.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class AdminAccountQuery {

	@Getter
	@Setter
	private String accountCode;
	
	@Getter
	@Setter
	private String roleId;
	
}
