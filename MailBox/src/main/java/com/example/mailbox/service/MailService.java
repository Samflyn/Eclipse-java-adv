package com.example.mailbox.service;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

@Service
public interface MailService {

	public void downloadMailConfigService() throws MessagingException;

}
