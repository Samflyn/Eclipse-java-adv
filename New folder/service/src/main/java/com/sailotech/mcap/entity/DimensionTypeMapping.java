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

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "dimension_type_mapping")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Data
@EqualsAndHashCode(of = "dimensionTypeMappigId", callSuper = false)
public class DimensionTypeMapping extends BaseDao implements Serializable {

	private static final long serialVersionUID = 1051183477852694710L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dimension_type_mapping_id ", unique = true, nullable = false)
	private Integer dimensionTypeMappigId;

	@Column(name = "demension_type", nullable = false)
	private Integer dimensionType;

	@Column(name = "demension_value")
	private String dimensionValue;
}
