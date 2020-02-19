package com.sailotech.mcap.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Nagendra
 *
 * @version 1.0
 *
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonIgnoreProperties(ignoreUnknown = true)

@Setter
@Getter
@EqualsAndHashCode(of = "headerId", callSuper = false)
public class InvoiceHeaderDto extends BaseDto implements Serializable {

	private static final long serialVersionUID = -4420170849360736852L;

	private Integer headerId;

	private Integer userId;

	private Integer deptId;

	private Integer companyId;

	private String invoiceNo;

	private String creditNote;

	private String poNumber;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date serviceDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date invoiceDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date deliveryDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date createdOn;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date lastUpdatedOn;

	private BigDecimal invoiceAmountGross;

	private BigDecimal invoiceAmountNt;

	private Integer workFlowStatus;

	private String channelType;

	private String errorDesc;

	private String rejectReason;

	private String ibanNo;

	private String accountNo;

	private String companyName;

	private String companyAddress;

	private String supplierIdentifier;

	private String vatId;

	private Integer nPages;

	private String transactionType;

	private UploadFileDetailsDto fileDetails;

	private List<InvoiceLinesDto> items;

}
