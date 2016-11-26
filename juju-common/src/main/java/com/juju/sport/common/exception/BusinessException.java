package com.juju.sport.common.exception;

import lombok.Getter;
import lombok.Setter;

import com.juju.sport.common.constants.BusinessExceptionDiscription;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -6729001295655350041L;

	@Getter
	@Setter
	private BusinessExceptionDiscription discription;
	
	public BusinessException(BusinessExceptionDiscription discription){
		super(discription.getMessage());
		this.discription = discription;
	}
	
}
