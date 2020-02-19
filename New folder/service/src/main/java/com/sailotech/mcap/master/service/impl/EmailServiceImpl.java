package com.sailotech.mcap.master.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.sailotech.mcap.dto.EmailConfigurationDto;
import com.sailotech.mcap.dto.MailConfig;
import com.sailotech.mcap.dto.MailContentModel;
import com.sailotech.mcap.entity.EmailConfiguration;
import com.sailotech.mcap.master.repository.MailConfigRepository;
import com.sailotech.mcap.master.service.EmailService;
import com.sailotech.mcap.util.MesserApAutomationUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private Configuration freemarkerConfig;

	@Autowired
	MailConfigRepository mailConfigRepo;

	@Autowired
	MesserApAutomationUtil messerApAutomationUtil;

	public JavaMailSender getJavaMailSender(EmailConfiguration emailConfig) {
		JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
		emailSender.setHost(emailConfig.getHost());
		emailSender.setPort(Integer.parseInt(emailConfig.getPort()));
		emailSender.setUsername(emailConfig.getUserName());
		emailSender.setPassword(emailConfig.getPassword());
		emailSender.setProtocol(emailConfig.getProtocol());
		emailSender.setDefaultEncoding(StandardCharsets.UTF_8.name());

		Properties props = emailSender.getJavaMailProperties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "false");

		emailSender.setJavaMailProperties(props);
		return emailSender;
	}

	@Override
	public String saveMailConfigs(EmailConfigurationDto mailConfigurationsMasterDto) {
		Integer loggedInUser = messerApAutomationUtil.getUserId();
		EmailConfiguration mailConfigMaster = new EmailConfiguration();
		BeanUtils.copyProperties(mailConfigurationsMasterDto, mailConfigMaster);
		if (mailConfigMaster.getMailConfigId() != null) {
			mailConfigMaster.setLastUpdatedBy(loggedInUser);
			mailConfigMaster.setLastUpdatedOn(Calendar.getInstance().getTime());
		} else {
			mailConfigMaster.setCreatedBy(loggedInUser);
			mailConfigMaster.setCreatedOn(Calendar.getInstance().getTime());
		}
		mailConfigRepo.save(mailConfigMaster);
		return messerApAutomationUtil.convertPojoToJson("Email Configuration saved successfully");
	}

	@Override
	public void sendMail(MailConfig mailModel, Integer companyId) throws MessagingException {
		EmailConfiguration emailConfig = mailConfigRepo.findByCompanyId(companyId);
		JavaMailSender emailSender = getJavaMailSender(emailConfig);
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		helper.setTo(mailModel.getTo());
		helper.setText(mailModel.getMessage(), true);
		helper.setSubject(mailModel.getSubject());
		helper.setFrom(emailConfig.getFromMail());
		emailSender.send(message);
	}

	@Override
	public String prepareMailContent(String action, MailContentModel mailContentModel)
			throws IOException, TemplateException {
		String mailContent = "";
		Template template = freemarkerConfig.getTemplate("forgotpasswordemail.ftl");
		mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailContentModel);
		return mailContent;
	}

	@Override
	public String getAllEmailConfigs() {
		List<EmailConfiguration> emailConfigurations = (List<EmailConfiguration>) mailConfigRepo.findAll();
		List<EmailConfigurationDto> emDtos = new ArrayList<>();
		for (EmailConfiguration configuration : emailConfigurations) {
			EmailConfigurationDto dto = new EmailConfigurationDto();
			BeanUtils.copyProperties(configuration, dto);
			emDtos.add(dto);
		}
		return messerApAutomationUtil.convertPojoToJson(emDtos);
	}

	@Override
	public void deleteUqcMapping(Integer mailConfigId) {
		mailConfigRepo.deleteById(mailConfigId);
	}
}
