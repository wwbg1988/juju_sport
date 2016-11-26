package com.juju.sport.game.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class RaceInfoQuery implements Serializable{

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
