package com.juju.sport.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.juju.sport.base.dto.MessageListDto;
import com.juju.sport.base.dto.MessagesDto;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;

@Service
public interface IMessageService {
	PageResult<MessagesDto> selectMessageby(MessagesDto messagesDto,PageQuery page);
	
	void insert(MessagesDto messagesDto);
	
	PageResult<MessagesDto> selectCommentsBy(MessagesDto messagesDto,PageQuery page);
	
	MessageListDto findAllMessageBy(MessagesDto messagesDto,PageQuery page);

	List<MessagesDto> queryMessagesBy(String userAccountId, int msgType);
}
