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
@Table(name = "uqc_mapping")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Setter
@EqualsAndHashCode(of = "uqcMappingId", callSuper = false)
public class UQCMapping extends BaseDao implements Serializable {

	private static final long serialVersionUID = -3747489452315588203L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uqc_mapping_id", unique = true, nullable = false)
	private Integer uqcMappingId;

	@Column(name = "uqc_code", nullable = true)
	private String uqcCode;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uqc_master_id", unique = true, nullable = false)
	private UQCMaster uqcMaster;

	@Column(name = "company_id", nullable = false)
	private Integer companyId;

}
