package com.juju.sport.admin.manger.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ActionDto {

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String action;

	@Getter
	@Setter
    private String actionDiscribtion;
}
