package com.juju.sport.admin.manger.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class RoleActionDto implements Serializable {

	private static final long serialVersionUID = -5924327185654100351L;
	
	@Getter
	@Setter
    private String roleId;

	@Getter
	@Setter
    private String actionId;
	
	@Getter
	@Setter
    private String action;

	@Getter
	@Setter
    private String actionDiscribtion;
    
	@Getter
	@Setter
	private Boolean selected;
}
