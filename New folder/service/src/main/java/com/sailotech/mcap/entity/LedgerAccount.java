package com.sailotech.mcap.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "ledger_account")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Setter
@EqualsAndHashCode(of = "ledgerAccount", callSuper = false)
public class LedgerAccount extends BaseDao implements Serializable {

	private static final long serialVersionUID = 1051183477852694710L;

	@Id
	@Column(name = "ledger_account", unique = true, nullable = false)
	private String ledgerAccount;

	@Column(name = "company_id", nullable = true)
	private Integer companyId;

	@Column(name = "ledger_account_desc")
	private String ledgerAccountDesc;

}
