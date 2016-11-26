package com.juju.sport.family.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SportExaminationDto {

	
	
	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String type;

	@Getter
	@Setter
    private String title;

	@Getter
	@Setter
    private String imgUrl;

	@Getter
	@Setter
    private Date createTime;

    
}