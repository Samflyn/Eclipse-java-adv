package com.sailotech.mcap.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "company")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Setter
@EqualsAndHashCode(of = "companyId", callSuper = false)
public class Company extends BaseDao implements Serializable {

	private static final long serialVersionUID = -2032956820153482621L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id", unique = true, nullable = false)
	private Integer companyId;

	@Column(name = "company_code", nullable = false)
	private String companyCode;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "registred_id", nullable = true)
	private String registredId;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "registred_address", nullable = true)
	private String registredAddress;

	@Column(name = "pincode")
	private Integer pincode;

	@Column(name = "mobile", nullable = true)
	private String mobile;

	@Column(name = "landline", nullable = true)
	private String landine;

	@Column(name = "fax", nullable = true)
	private String fax;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "state_id", nullable = true)
	private StateMaster stateMaster;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "country_id", nullable = true)
	private CountryMaster countryMaster;

	@Column(name = "email_id")
	private String emailId;
}
