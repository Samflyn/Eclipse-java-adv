package com.sailotech.mcap.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Nagendra
 *
 * @version 1.0
 *
 */
@Entity
@Table(name = "invoice_header")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Setter
@EqualsAndHashCode(of = "headerId", callSuper = false)
public class InvoiceHeader extends BaseDao implements Serializable {

	private static final long serialVersionUID = 1051183477852694710L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "header_id", unique = true, nullable = false)
	private Integer headerId;

	/* PO Invoice Headers */
	@Column(name = "credit_note", nullable = true)
	private String creditNote;

	@Column(name = "po_number", nullable = true)
	private String poNumber;

	/* Cost invoice */
	@Column(name = "service_date", nullable = true)
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date serviceDate;

	/* common Invoice Heades */
	@Column(name = "invoice_date", nullable = true)
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date invoiceDate;

	@Column(name = "delivery_date", nullable = true)
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date deliveryDate;

	@Column(name = "supplier_name", nullable = true, length = 90)
	private String supplierName;

	@Column(name = "supplier_address")
	private String supplierAddress;

	@Column(name = "channel_type")
	private String channelType;

	@Column(name = "user_id", nullable = true)
	private Integer userId;

	@Column(name = "dept_id", nullable = true)
	private Integer deptId;

	@Column(name = "company_id", nullable = true)
	private Integer companyId;

	@Column(name = "invoice_amount_nt", nullable = true, length = 15)
	private BigDecimal invoiceAmountNt;

	@Column(name = "invoice_amount_gross", nullable = true, length = 15)
	private BigDecimal invoiceAmountGross;

	@Column(name = "error_desc", nullable = true)
	private String errorDesc;

	@Column(name = "work_flow_status", nullable = false)
	private Integer workFlowStatus;

	@Column(name = "account_no")
	private String accountNo;

	@Column(name = "invoice_no")
	private String invoiceNo;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "header_id", nullable = true)
	private UploadFileDetails fileDetails;

	@Column(name = "company_address")
	private String companyAddress;

	@Column(name = "company_name")
	private String companyame;

	@Column(name = "transaction_type")
	private String transactionType;

	@Column(name = "n_pages")
	private Integer nPages;

	@Column(name = "iban_no")
	private String ibanNo;

	@Column(name = "vat_id")
	private String vatId;

	@Column(name = "reject_reason")
	private String rejectReason;

	@Column(name = "supplier_identifier")
	private String supplierIdentifier;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "header_id", referencedColumnName = "header_id", nullable = true)
	private List<InvoiceLines> items;

}
