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
@Table(name = "item_mapping")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Setter
@EqualsAndHashCode(of = "itemMappingId", callSuper = false)
public class ItemMapping extends BaseDao implements Serializable {

	private static final long serialVersionUID = -879614223255925347L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_mapping_id", unique = true, nullable = false)
	private Integer itemMappingId;

	@Column(name = "item_mapping_code", nullable = true)
	private String itemMappingCode;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_master_id", nullable = false)
	private ItemMaster itemMaster;

	@Column(name = "company_id", nullable = false)
	private Integer companyId;

}
