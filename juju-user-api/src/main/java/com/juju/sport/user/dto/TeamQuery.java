package com.juju.sport.user.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class TeamQuery implements Serializable{

	@Getter
	@Setter
	private String teamName;
	
	@Getter
	@Setter
	private String sportType;
	
}
