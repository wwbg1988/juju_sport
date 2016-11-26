package com.juju.sport.user.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class SportJsonDto implements Serializable{
	@Setter
	@Getter
	public List<SportJsonList> resultList;
}


