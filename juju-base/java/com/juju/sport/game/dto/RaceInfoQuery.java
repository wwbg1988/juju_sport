package com.juju.sport.game.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class RaceInfoQuery {

	@Getter
	@Setter
    private String sportTypeId;
	
	@Getter
	@Setter
    private String title;
	
	@Getter
	@Setter
    private String organizers;
}
