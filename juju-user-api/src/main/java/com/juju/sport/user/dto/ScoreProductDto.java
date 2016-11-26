package com.juju.sport.user.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class ScoreProductDto implements Serializable{

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String productName;

	@Getter
	@Setter
    private Integer marketPrice;

	@Getter
	@Setter
    private Integer needScore;

	@Getter
	@Setter
    private Integer status;

	@Getter
	@Setter
    private String productImage;

	@Getter
	@Setter
    private Date createTime;

	@Getter
	@Setter
    private Date lastUpdateTime;

	@Getter
	@Setter
    private Integer stat;

	@Getter
	@Setter
    private String orderBy="desc";//排序 desc asc

	@Getter
	@Setter
    private String orderColumn = "create_time";//根据那个字段排序

}
