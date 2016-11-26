package com.juju.sport.game.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class RaceTeamQuery implements Serializable{

	@Getter
	@Setter
	private String raceId;
	
	@Getter
	@Setter
	private String teamId;
	
	@Getter
	@Setter
	private String raceTeamName;
	
	@Getter
	@Setter
	private String sportType;
	
}
