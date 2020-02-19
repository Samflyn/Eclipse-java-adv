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
@Table(name = "uqc_master")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Setter
@EqualsAndHashCode(of = "uqcMasterId", callSuper = false)
public class UQCMaster extends BaseDao implements Serializable {

	private static final long serialVersionUID = -3242479943682492270L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uqc_master_id", unique = true, nullable = false)
	private Integer uqcMasterId;

	@Column(name = "uqc_code", nullable = true)
	private String uqcCode;

	@Column(name = "uqc_desc", nullable = true)
	private String uqcDesc;

	@Column(name = "company_id", nullable = false)
	private Integer companyId;
}
