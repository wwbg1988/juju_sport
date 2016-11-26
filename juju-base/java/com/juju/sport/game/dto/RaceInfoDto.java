package com.juju.sport.game.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class RaceInfoDto {

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String sportTypeId;
	
	@Getter
	@Setter
	private String sportName;

	@Getter
	@Setter
    private String title;

	@Getter
	@Setter
    private String pic;

	@Getter
	@Setter
    private String organizers;

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
    private String context;
	
}
