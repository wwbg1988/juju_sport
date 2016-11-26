package com.juju.sport.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class SpaceMessagesDto implements Serializable{
	
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private int msgType;
	
	@Getter
	@Setter
	private String msgFromId;
	
	@Getter
	@Setter
	private String msgToId;
	
	@Getter
	@Setter
	private Date msgTime;
	
	@Getter
	@Setter
	private String attachment;
	
	@Getter
	@Setter
	private int msgStatus;
	
	@Getter
	@Setter
	private Date createTime;
	
	@Getter
	@Setter
	private Date lastUpdateTime;
	
	@Getter
	@Setter
	private int stat;
	
	@Getter
	@Setter
	private String msgContent;
	
	@Getter
	@Setter
	private String userAccount;
	
}
