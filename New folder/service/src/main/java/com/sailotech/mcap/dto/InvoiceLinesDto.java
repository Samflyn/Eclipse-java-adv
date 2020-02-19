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
public class InvoiceLinesDto extends BaseDto implements Serializable {

	private static final long serialVersionUID = -3973658755150396667L;

	private Integer lineId;
	
	private Integer headerId;
	
	private String ponumber;

	private String itemDesc;
		
	private String uqc;
	
	private Double qty;

	private Double taxAmount;
	
	private Double itemRate;
	
	private Double itemCode;

	private Double rate;

	private Double lineValue;

	private String currency;

	private String errorDesc;
	
	private String vehiclePlateNo;
	
	private String costCenterCode;
	
	private String costAccountMgr;
	
	

}
