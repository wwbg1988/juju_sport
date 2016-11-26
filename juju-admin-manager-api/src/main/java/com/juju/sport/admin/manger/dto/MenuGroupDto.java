package com.juju.sport.admin.manger.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class MenuGroupDto  implements Serializable {

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String menuGroupName;

}
