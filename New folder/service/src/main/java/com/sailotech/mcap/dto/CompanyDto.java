package com.sailotech.mcap.dto;

import java.io.Serializable;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sailotech.mcap.entity.CountryMaster;
import com.sailotech.mcap.entity.StateMaster;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Setter
@EqualsAndHashCode(of = "companyId", callSuper = false)
public class CompanyDto implements Serializable {

	private static final long serialVersionUID = -2032956820153482621L;

	private Integer companyId;

	private String companyCode;

	private String companyName;

	private String registredId;

	private String description;

	private String registredAddress;

	private Integer pincode;

	private String mobile;

	private String landine;

	private String fax;

	private StateMaster stateMasterDto;

	private CountryMaster countryMasterDto;

	private String emailId;
}
