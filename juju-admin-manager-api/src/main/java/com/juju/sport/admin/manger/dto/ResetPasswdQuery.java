package com.juju.sport.admin.manger.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ResetPasswdQuery {
	
	@Getter
	@Setter
	private String accountCode;

	@Getter
	@Setter
	private String oldPW;
	
	@Getter
	@Setter
	private String newPW;
	
	@Getter
	@Setter
	private String newPWAgain;
		
}
