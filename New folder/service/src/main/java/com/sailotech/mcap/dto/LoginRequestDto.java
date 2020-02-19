package com.sailotech.mcap.dto;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class LoginRequestDto implements Serializable {

	private static final long serialVersionUID = -3359575242802190772L;

	private String username;
	private String password;
}
