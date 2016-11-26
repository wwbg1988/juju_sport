package com.juju.sport.admin.manger.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class AdminAccountDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1427715099495981983L;

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String userName;

	@Getter
	@Setter
    private String passwd;

	@Getter
	@Setter
    private String realName;

	@Getter
	@Setter
    private String roleId;
	
	@Getter
	@Setter
	private String roleName;
	
}
