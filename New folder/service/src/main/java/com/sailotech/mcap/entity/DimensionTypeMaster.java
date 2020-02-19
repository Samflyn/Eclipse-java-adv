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

@Entity
@Table(name = "dimension_type_master")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Setter
@EqualsAndHashCode(of = "dimensionType", callSuper = false)
public class DimensionTypeMaster extends BaseDao implements Serializable {

	private static final long serialVersionUID = 6025685496299338605L;

	@Id
	@Column(name = "dimension_type", unique = true, nullable = false)
	private Integer dimensionType;

	@Column(name = "dimension_type_name", nullable = true)
	private String dimensionTypeName;

}
