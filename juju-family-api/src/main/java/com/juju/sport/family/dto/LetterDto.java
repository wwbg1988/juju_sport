package com.juju.sport.family.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class LetterDto implements Serializable{

	
	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String letter;

	@Getter
	@Setter
    private String data;

	@Getter
	@Setter
    private Date createTime;

	@Getter
	@Setter
    private Date lastUpdateTime;

    
}