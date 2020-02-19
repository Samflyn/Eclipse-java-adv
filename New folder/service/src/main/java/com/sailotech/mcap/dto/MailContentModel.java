package com.sailotech.mcap.dto;

import lombok.Data;

@Data
public class MailContentModel {

	private String userName;
	private String otp;
	private String mailDate;
	private String validUpto;

}
