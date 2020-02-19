package com.sailotech.mcap.dto;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

@Data
public class MailConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	private String from;
	private String to;
	private String subject;
	private String message;
	private Map<String, String> hashMap;
}
