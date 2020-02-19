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

/**
 * @author Nagendra
 *
 * @version 1.0
 *
 */
@Entity
@Table(name = "ledger_account_dimension_master")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class LedgerAccountDimensionMaster extends BaseDao implements Serializable {

	private static final long serialVersionUID = 1051183477852694710L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "ledger_account", nullable = false)
	private String ledgerAccount;

	@Column(name = "dimension_type", nullable = false)
	private Integer dimensionType;

	@Column(name = "dimension_required")
	private String dimensionRequired;

}
