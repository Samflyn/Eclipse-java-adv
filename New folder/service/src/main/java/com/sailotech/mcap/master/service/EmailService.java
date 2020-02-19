package com.sailotech.mcap.master.service;

import java.io.IOException;

import javax.mail.MessagingException;

import com.sailotech.mcap.dto.EmailConfigurationDto;
import com.sailotech.mcap.dto.MailConfig;
import com.sailotech.mcap.dto.MailContentModel;

import freemarker.template.TemplateException;

public interface EmailService {

	public String saveMailConfigs(EmailConfigurationDto mailConfigurationsMasterDto);

	public void sendMail(MailConfig mailModel, Integer companyId) throws MessagingException;

	String prepareMailContent(String action, MailContentModel mailContentModel) throws IOException, TemplateException;

	public String getAllEmailConfigs();

	public void deleteUqcMapping(Integer mailConfigId);

}
