package com.juju.sport.base.service;

import org.springframework.stereotype.Service;

@Service
public interface IEmailSendService {
	public void sendEmail(int randomNumber,String email,String content, String from_, String subject);
}
