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
public class UQCMappingDto extends BaseDto implements Serializable {

	private static final long serialVersionUID = 4716087876346016255L;

	private Integer uqcMappingId;

	private String uqcCode;

	private UQCMasterDto uqcMasterDto;

	private Integer companyId;

}
