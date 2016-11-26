package com.juju.sport.family.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class SpecificationsDto implements Serializable{
	
	
	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String sex;

	@Getter
	@Setter
    private Integer age;

	@Getter
	@Setter
    private Double height;

	@Getter
	@Setter
    private Double weight;

    
}