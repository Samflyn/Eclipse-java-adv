package com.example.mailbox.serviceimpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Props {

	
	public static final int BUFFER_SIZE = 4096;

	public static String saveDirectory;

	public static String hostname;

	public static String port;

	public static String username;
	
	public static String password;
	
	public static String extensions;

	public static String protocol;

	public static String folder = "INBOX";

	public static String emailFile ;
	
	public static String FILE_TYPE_ZIP = "zip";

	@Value("${file.output.save}")
	public void setSaveDirectory(String saveDirectory) {
		this.saveDirectory = saveDirectory;
	}

	@Value("${file.email.input}")
	public  void setEmailFile(String emailFile) {
		Props.emailFile = emailFile;
	}


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

	
}
