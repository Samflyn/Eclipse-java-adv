package com.sailotech.mailbox.service;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
	
public String downloadMailConfigService() throws MessagingException;
}
