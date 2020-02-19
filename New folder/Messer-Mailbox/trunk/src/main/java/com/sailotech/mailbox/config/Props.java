package com.sailotech.mailbox.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class Props {

	public static final int BUFFER_SIZE = 4096;
	public static String hostname;

	public static String port;

	public static String username;

	public static String password;

	public static String extensions;

	public static String protocol;

	

	@Value("${mail.attach.dest.dir}")
	public String mailBoxDestDir;
	
	@Value("${mail.attach.temp.dir}")
	public String mailBoxTempDir;

	@Value("${mail.config.host}")
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	@Value("${mail.config.port}")
	public void setPort(String port) {
		this.port = port;
	}

	@Value("${mail.config.username}")
	public void setUsername(String username) {
		this.username = username;
	}

	@Value("${mail.config.password}")
	public void setPassword(String password) {
		this.password = password;
	}

	@Value("#{'${file.valid.extensions}'.split(',')}")
	public void setExtensions(String extensions) {
		this.extensions = extensions;
	}

	@Value("${mail.config.protocal}")
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	
	public String getMailBoxDestDir() {
		return mailBoxDestDir;
	}

	public void setMailBoxDestDir(String mailBoxDestDir) {
		this.mailBoxDestDir = mailBoxDestDir;
	}

	public String getMailBoxTempDir() {
		return mailBoxTempDir;
	}

	public void setMailBoxTempDir(String mailBoxTempDir) {
		this.mailBoxTempDir = mailBoxTempDir;
	}

	
}
