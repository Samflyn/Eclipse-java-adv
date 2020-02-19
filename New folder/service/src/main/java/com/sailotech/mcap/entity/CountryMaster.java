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
@Table(name = "country_master")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Setter
@EqualsAndHashCode(of = "countryId", callSuper = false)
public class CountryMaster extends BaseDao implements Serializable {

	private static final long serialVersionUID = 6025685496299338605L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "country_id ", unique = true, nullable = false)
	private Integer countryId;

	@Column(name = "country_code", nullable = true)
	private String countryCode;

	@Column(name = "country_name", nullable = true)
	private String countryName;

}
