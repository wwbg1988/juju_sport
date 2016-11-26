package com.juju.sport.game.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class RaceScoreboardDto implements Serializable{

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String teamId;

	@Getter
	@Setter
    private String teamName;

	@Getter
	@Setter
    private String raceInfoId;

	@Getter
	@Setter
    private String raceInfoTitle;

	@Getter
	@Setter
    private String teamGroup;

	@Getter
	@Setter
    private Integer won;

	@Getter
	@Setter
    private Integer drawn;

	@Getter
	@Setter
    private Integer lost;

	@Getter
	@Setter
    private Integer goalsScored;

	@Getter
	@Setter
    private Integer goalsAgainst;

	@Getter
	@Setter
    private Integer goalsDifference;

	@Getter
	@Setter
    private Float points;

	@Getter
	@Setter
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;

	@Getter
	@Setter
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date lastUpdateTime;

	@Getter
	@Setter
    private Integer stat;
}
