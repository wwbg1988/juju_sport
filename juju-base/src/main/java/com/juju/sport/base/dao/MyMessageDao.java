package com.juju.sport.base.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.juju.sport.base.dto.MessagesDto;
import com.juju.sport.base.mapper.MessageExMapper;
import com.juju.sport.base.mapper.MessagesMapper;
import com.juju.sport.base.pojo.Messages;
import com.juju.sport.base.pojo.MessagesExample;
import com.juju.sport.common.constants.DataStatus;
import com.juju.sport.common.model.PageQuery;
import com.juju.sport.common.mybatis.MyBatisBaseDao;

@Repository
public class MyMessageDao extends MyBatisBaseDao<Messages> {
	
	@Autowired
	@Getter
	private MessageExMapper messageExMapper;
	
	@Autowired
	@Getter
	private MessagesMapper mapper;

	public List<Messages> selectMessageby(Messages messages,
			PageQuery page) {
			MessagesExample example = new MessagesExample();
			MessagesExample.Criteria criteria = example.createCriteria();
//			long starNum = page.getStartNum();  
//			int pageSize = page.getPageSize();
//			if (null == messages.getUserAccount()){
//				return  messageExMapper.selectSysMessages(starNum,pageSize);
//			}
			if(!StringUtils.isEmpty(messages.getUserAccount())){
			criteria.andUserAccountEqualTo(messages.getUserAccount());
			}
			
			if(!StringUtils.isEmpty(messages.getMsgType())){
				criteria.andMsgTypeEqualTo(messages.getMsgType());
			}
//			else{
//				criteria.andMsgTypeEqualTo(1);  
//			}
			
			if(!StringUtils.isEmpty(messages.getMsgToId())){
				criteria.andMsgToIdEqualTo(messages.getMsgToId());
			}
			criteria.andStatEqualTo(DataStatus.ENABLED);
			example.setOrderByClause("create_time desc limit " + page.getStartNum() + ", " + page.getPageSize());
			return mapper.selectByExample(example);
		}

	public long selectAllMessages(MessagesDto messages) {
		MessagesExample example = new MessagesExample();
		MessagesExample.Criteria criteria = example.createCriteria();
		
		if(!StringUtils.isEmpty(messages.getUserAccount())){
			criteria.andUserAccountEqualTo(messages.getUserAccount());
		}
			
			if(!StringUtils.isEmpty(messages.getMsgType())){
				criteria.andMsgTypeEqualTo(messages.getMsgType());
			}else{
				criteria.andMsgTypeEqualTo(1);  
			}
			
			if(!StringUtils.isEmpty(messages.getMsgToId())){
				criteria.andMsgToIdEqualTo(messages.getMsgToId());
			}
			criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}

	public List<Messages> queryMessagesBy(String userAccountId, int msgType) {
		MessagesExample example = new MessagesExample();
		MessagesExample.Criteria criteria = example.createCriteria();
		
		criteria.andMsgTypeEqualTo(msgType);
		
		criteria.andMsgFromIdEqualTo(userAccountId);
		
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause("create_time desc limit 1");
		return mapper.selectByExample(example);
	}


}
