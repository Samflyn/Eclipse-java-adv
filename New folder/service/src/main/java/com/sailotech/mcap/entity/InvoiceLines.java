package com.sailotech.mcap.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "invoice_lines")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Setter
@EqualsAndHashCode(of = "lineId", callSuper = false)
public class InvoiceLines extends BaseDao implements Serializable {

	private static final long serialVersionUID = -3973658755150396667L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "line_id", unique = true, nullable = false)
	private Integer lineId;
	
	@Column(name = "header_id", unique = true, nullable = false)
	private Integer headerId;
	
	@Column(name = "po_number", nullable = true)
	private String ponumber;
	
	@Column(name = "item_desc", nullable = true)
	private String itemDesc;
		
	@Column(name = "uqc", nullable = true)
	private String uqc;
	
	@Column(name = "qty", nullable = true, length = 15)
	private Double qty;

	@Column(name = "tax_amount", nullable = true, length = 15)
	private Double taxAmount;
	
	@Column(name = "item_rate", nullable = true, length = 15)
	private Double itemRate;
	
	@Column(name = "item_code", nullable = true, length = 15)
	private Double itemCode;

	@Column(name = "tax_rate", nullable = true, length = 15)
	private Double rate;

	@Column(name = "line_value", nullable = true, length = 15)
	private Double lineValue;

	@Column(name = "currency")
	private String currency;

	@Column(name = "error_desc", nullable = true)
	private String errorDesc;
	
	@Column(name = "vehicle_plate_no")
	private String vehiclePlateNo;
	
	@Column(name = "cost_center_code")
	private String costCenterCode;
	
	@Column(name = "cost_account_mgr")
	private String costAccountMgr;
	
	
	


}
