package com.juju.sport.game.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class RaceListDto implements Serializable{
	
	@Getter
	@Setter
	private RaceInfoDto raceInfoDto;
	
	@Getter
	@Setter
	private List<RaceTeamDto> raceTeamListDto;
}
