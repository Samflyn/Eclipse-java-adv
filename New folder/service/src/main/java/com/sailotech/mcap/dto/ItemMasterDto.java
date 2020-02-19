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
public class ItemMasterDto extends BaseDto implements Serializable {

	private static final long serialVersionUID = 7769796712543763648L;

	private Integer itemId;

	private String itemName;

	private String itemDesc;

	private String itemType;

	private String hsnCode;

	private String goodsServices;

	private Integer rate;

	private Integer companyId;

}
