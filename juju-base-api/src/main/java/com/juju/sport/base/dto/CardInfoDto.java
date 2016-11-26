package com.juju.sport.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class CardInfoDto implements Serializable{

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String cardNo;

	@Getter
	@Setter
    private String cardName;

	@Getter
	@Setter
    private String cardId;

	@Getter
	@Setter
    private String telephone;

	@Getter
	@Setter
    private String age;

	@Getter
	@Setter
    private String address;

	@Getter
	@Setter
    private String contact;

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
