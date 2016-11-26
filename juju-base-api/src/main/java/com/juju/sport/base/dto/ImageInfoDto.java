package com.juju.sport.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ImageInfoDto implements Serializable{

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String name;

	@Getter
	@Setter
    private String url;
	
	@Getter
	@Setter
	private String infactUrl;

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
