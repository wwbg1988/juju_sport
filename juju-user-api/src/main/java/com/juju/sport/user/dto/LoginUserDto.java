package com.juju.sport.user.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class LoginUserDto implements Serializable{
	@Setter
	@Getter
	private String id;
	
	@Getter
	@Setter
	private int type;
}
