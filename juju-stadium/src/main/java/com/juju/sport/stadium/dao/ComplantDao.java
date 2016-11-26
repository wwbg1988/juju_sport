package com.juju.sport.stadium.dao;

import java.util.Date;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juju.sport.base.dto.SpaceMessagesDto;
import com.juju.sport.base.mapper.MessagesMapper;
import com.juju.sport.base.pojo.Messages;
import com.juju.sport.common.mybatis.MyBatisBaseDao;
import com.juju.sport.common.util.BeanUtils;
import com.juju.sport.common.util.UUIDGenerator;

@Repository
public class ComplantDao extends MyBatisBaseDao<Messages>{
	@Autowired
	@Getter
	private MessagesMapper mapper;
	
	public void insertComplant(SpaceMessagesDto spaceMessagesDto){
		spaceMessagesDto.setId(UUIDGenerator.getUUID());
		spaceMessagesDto.setCreateTime(new Date());
		spaceMessagesDto.setLastUpdateTime(new Date());
		Messages spaceMessages = new Messages();
		BeanUtils.copyProperties(spaceMessagesDto, spaceMessages);
		spaceMessages.setStat(1);
		mapper.insert(spaceMessages);
	}
}
