package com.juju.sport.family.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportIntroductionDto implements Serializable{

	
	
	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String imgUrl;

	@Getter
	@Setter
    private Integer type;

	@Getter
	@Setter
    private String sport;

	@Getter
	@Setter
    private Integer environmental;

	@Getter
	@Setter
    private Integer sex;

	@Getter
	@Setter 
    private String descde;

	@Getter
	@Setter
    private String videoUrl;

	@Getter
	@Setter  
    private Date createTime;

    
}