package com.juju.sport.base.service;

import org.springframework.stereotype.Service;

@Service
public interface ISmsSendService {
	public String sendSms(int randomNumber,String mobilePhone,String content);
}
