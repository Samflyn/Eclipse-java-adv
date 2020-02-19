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
@Table(name = "item_master")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Setter
@EqualsAndHashCode(of = "itemId", callSuper = false)
public class ItemMaster extends BaseDao implements Serializable {

	private static final long serialVersionUID = 7769796712543763648L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id", unique = true, nullable = false)
	private Integer itemId;

	@Column(name = "item_name", nullable = true)
	private String itemName;

	@Column(name = "item_desc", nullable = true)
	private String itemDesc;

	@Column(name = "item_type", nullable = true)
	private String itemType;

	@Column(name = "hsn_sac_code", nullable = false)
	private String hsnCode;

	@Column(name = "goods_services", nullable = true)
	private String goodsServices;

	@Column(name = "rate", nullable = true)
	private Integer rate;

	@Column(name = "company_id", nullable = true)
	private Integer companyId;

}
