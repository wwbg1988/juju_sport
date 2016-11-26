package com.juju.sport.stadium.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juju.sport.base.dto.SpaceMessagesDto;
import com.juju.sport.stadium.dao.ComplantDao;
import com.juju.sport.stadium.service.IComplaintService;

@Service
public class ComplantServiceImpl implements IComplaintService{

	@Autowired
	private ComplantDao complantDao;
	
	@Override
	public void insertComplaint(SpaceMessagesDto spaceMessagesDto) {
		complantDao.insertComplant(spaceMessagesDto);
	}
	
}
