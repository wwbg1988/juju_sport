package com.juju.sport.home.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class TicketDto implements Serializable{
	@Getter
	@Setter
	private String dimensionImage;
	@Getter
	@Setter
	private String randCode;

}
