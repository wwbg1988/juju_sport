package com.juju.sport.user.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class SportJsonList implements Serializable{
	@Setter
	@Getter
	private String sportId;

	@Setter
	@Getter
	private String sportName;
}
