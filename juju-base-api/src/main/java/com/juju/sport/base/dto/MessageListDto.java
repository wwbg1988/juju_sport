package com.juju.sport.base.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.juju.sport.common.model.PageQuery;

public class MessageListDto implements Serializable{
	
	@Getter
	@Setter
	private List<MessagesDto> messList;
	
	@Getter
	@Setter
	private PageQuery page;
}
