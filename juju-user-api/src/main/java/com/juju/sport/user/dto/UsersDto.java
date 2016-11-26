package com.juju.sport.user.dto;

import lombok.Setter;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

@ToString
public class UsersDto implements Serializable{

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String userAccountId;
	
	@Getter
	@Setter
	private String userAccount;

	@Getter
	@Setter
	private String realName;

	@Getter
	@Setter
	private Integer job;
	
	@Getter
	@Setter
	private String jobName;

	@Getter
	@Setter
	private Integer provinceid;
	
	@Getter
	@Setter
	private String provinceName;

	@Getter
	@Setter
	private Integer cityid;
	
	@Getter
	@Setter
	private String cityName;

	@Getter
	@Setter
	private Integer countryid;
	
	@Getter
	@Setter
	private String countryName;

	@Getter
	@Setter
	private String address;

	@Getter
	@Setter
	private String email;

	@Getter
	@Setter
	private String mobileNo;

	@Getter
	@Setter
	private Integer age;

	@Getter
	@Setter
	private String userImage;

	@Getter
	@Setter
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date brithday;

	@Getter
	@Setter
	private String nickName;

	@Getter
	@Setter
	private Integer chargeType;

	@Getter
	@Setter
	private String venueType;
	
	@Getter
	@Setter
	private String venueTypeNames;

	@Getter
	@Setter
	private Integer userLevel;

	@Getter
	@Setter
	private Integer userScore;

	@Getter
	@Setter
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createTime;

	@Getter
	@Setter
	private Date lastUpdateTime;

	@Getter
	@Setter
	private Integer stat;
	
	@Getter
	@Setter
	private Float height;
	
	@Getter
	@Setter
	private Float weight;

	@Getter
	@Setter
	private String company;
	
	@Getter
	@Setter
    private String telephone;
	
	@Getter
	@Setter
	private String documentNo;
	
	@Getter
	@Setter
	private String cardImage;

}
