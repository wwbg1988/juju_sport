package com.juju.sport.family.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class HealthDto implements Serializable{
	
	
	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String healthKnowledge;

	@Getter
	@Setter
    private Date createTime;

    
}