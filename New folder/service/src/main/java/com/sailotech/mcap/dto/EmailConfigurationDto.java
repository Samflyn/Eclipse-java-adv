package com.sailotech.mcap.dto;

import java.io.Serializable;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class EmailConfigurationDto implements Serializable {

	private static final long serialVersionUID = 7773277476375111825L;

	private Integer mailConfigId;

	private String protocol;

	private String host;

	private String port;

	private String userName;

	private String password;

	private String fromMail;

	private Integer companyId;

	private String companyName;

}
