package com.juju.sport.game.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class RaceTeamDto implements Serializable{
	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String teamName;

	@Getter
	@Setter
    private String thumbnail;

	@Getter
	@Setter
    private Integer warType;
	
	@Getter
	@Setter
	private String warTypeName;

	@Getter
	@Setter
    private String sportId;
	
	@Getter
	@Setter
	private String sportName;

	@Getter
	@Setter
    private String contact;

	@Getter
	@Setter
    private String orderId;

	@Getter
	@Setter
    private String userAccountId;
	
	@Getter
	@Setter
	private String userAccountName;

	@Getter
	@Setter
    private Integer maxNum;

	@Getter
	@Setter
    private Integer joinNum;

	@Getter
	@Setter
    private String createTime;

	@Getter
	@Setter
    private String lastUpdateTime;

	@Getter
	@Setter
    private Integer stat;

	@Getter
	@Setter
    private String warDesc;

	@Getter
	@Setter
	private List<String> teamIds;
	
	@Getter
	@Setter
    private String nameandtime;
	
	@Getter
	@Setter
	private String raceId;
}
