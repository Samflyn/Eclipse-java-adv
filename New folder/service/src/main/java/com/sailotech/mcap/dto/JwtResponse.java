package com.sailotech.mcap.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = 2471306024196487877L;
	private String token;
	private String type = "Bearer";
	private UserDto user;

	public JwtResponse(String accessToken, UserDto user) {
		this.token = accessToken;
		this.user = user;
	}
}
