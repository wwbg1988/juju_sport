package com.juju.sport.admin.manger.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class AdminMenuFunctionDto implements Serializable{
   
	private static final long serialVersionUID = -5642755134455786064L;

	@Getter
	@Setter
	private String id;
    
	@Getter
	@Setter
    private String menuGroupId;
    
	@Getter
	@Setter
    private String menuGroupName;

	@Getter
	@Setter
    private String menuFunctionName;
   
	@Getter
	@Setter
    private String menuFunctionAction;

	@Getter
	@Setter
    private Integer openType;
    
	@Getter
	@Setter
    private String menuFunctionDescript;

}