package com.juju.sport.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class HomePageConfigDto implements Serializable{

	@Getter
	@Setter
    private String id;
	
	@Getter
	@Setter
	private String page;

	@Getter
	@Setter
    private String dataKey;

	@Getter
	@Setter
    private String dataValue;

	@Getter
	@Setter
    private Integer orderTag;

	@Getter
	@Setter
    private Date createTime;

	@Getter
	@Setter
    private Date lastUpdateTime;

	@Getter
	@Setter
    private Integer stat;
}
