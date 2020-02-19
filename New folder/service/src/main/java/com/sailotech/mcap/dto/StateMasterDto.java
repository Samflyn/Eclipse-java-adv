package com.sailotech.mcap.dto;

import java.io.Serializable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Setter
public class StateMasterDto extends BaseDto implements Serializable {

	private static final long serialVersionUID = 7739941156013420386L;

	private Integer stateId;
	
	private String stateCode;
	
	private String stateName;

}
