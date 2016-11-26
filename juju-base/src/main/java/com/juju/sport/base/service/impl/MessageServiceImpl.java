package com.juju.sport.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.base.dao.MyMessageDao;
import com.juju.sport.base.dto.MessageListDto;
import com.juju.sport.base.dto.MessagesDto;
import com.juju.sport.base.pojo.Messages;
import com.juju.sport.base.service.IMessageService;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.model.PageResult;
import com.juju.sport.common.util.BeanUtils;

@Service
public class MessageServiceImpl implements IMessageService {

	@Autowired
	private MyMessageDao myMessageDao;

	@Override
	public PageResult<MessagesDto> selectMessageby(MessagesDto messagesDto,PageQuery page) {
		Messages messages = new Messages();
		BeanUtils.copyProperties(messagesDto, messages);
		List<Messages> messagesList = myMessageDao.selectMessageby(messages,page);
		List<MessagesDto> messagesDtoList = new ArrayList<MessagesDto>();
		messagesDtoList = BeanUtils.createBeanListByTarget(messagesList,Messages.class);
		long total = myMessageDao.selectAllMessages(messagesDto);
		page.setTotal(total);
		return new PageResult<MessagesDto>(page, messagesDtoList);
	}

	@Override
	public void insert(MessagesDto messagesDto) {
		Messages messages = new Messages();
		BeanUtils.copyProperties(messagesDto, messages);
		myMessageDao.insert(messages);
	}

	@Override
	public PageResult<MessagesDto> selectCommentsBy(MessagesDto messagesDto,
			PageQuery page) {
		Messages messages = new Messages();
		BeanUtils.copyProperties(messagesDto, messages);
		List<Messages> messagesList = myMessageDao.selectMessageby(messages,
				page);

		List<MessagesDto> messagesDtoList = new ArrayList<MessagesDto>();
		messagesDtoList = BeanUtils.createBeanListByTarget(messagesList,
				Messages.class);
		long total = myMessageDao.selectAllMessages(messagesDto);
		page.setTotal(total);
		return new PageResult<MessagesDto>(page, messagesDtoList);
	}

	@Override
	public MessageListDto findAllMessageBy(MessagesDto messagesDto,
			PageQuery page) {
		MessageListDto messageListDto = new MessageListDto();
		Messages messages = new Messages();
		BeanUtils.copyProperties(messagesDto, messages);
		List<Messages> messagesList = myMessageDao.selectMessageby(messages,
				page);

		List<MessagesDto> messagesDtoList = new ArrayList<MessagesDto>();
		messagesDtoList = BeanUtils.createBeanListByTarget(messagesList,
				MessagesDto.class);
		long total = myMessageDao.selectAllMessages(messagesDto);
		page.setTotal(total);
		messageListDto.setMessList(messagesDtoList);
		messageListDto.setPage(page);
		return messageListDto;
	}

	@Override
	public List<MessagesDto> queryMessagesBy(String userAccountId, int msgType) {
		List<Messages> dataList = myMessageDao.queryMessagesBy(userAccountId,msgType);
		List<MessagesDto> messagesDtoList = new ArrayList<MessagesDto>();
		messagesDtoList = BeanUtils.createBeanListByTarget(dataList,MessagesDto.class);
		return messagesDtoList;
	}
}
