package com.juju.sport.admin.manger.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class RoleMenuMappingDto implements Serializable {
	
	private static final long serialVersionUID = 1130803110110259368L;

	public RoleMenuMappingDto(){
		
	}
	
	@Getter
	@Setter
	private String menuId;
	
	@Getter
	@Setter
	private String groupName; 
	
	@Getter
	@Setter
	private String menuName;
	
	@Getter
	@Setter
	private String menuAction;
	
	@Getter
	@Setter
	private String roleId;
	
	@Getter
	@Setter
	private Boolean selected;
}
