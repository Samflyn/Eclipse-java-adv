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
@Table(name = "department")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Setter
@EqualsAndHashCode(of = "deptId", callSuper = false)
public class Department extends BaseDao implements Serializable {

	private static final long serialVersionUID = 1051183477852694710L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "deptId", unique = true, nullable = false)
	private Integer deptId;

	@Column(name = "dept_code")
	private String deptCode;

	@Column(name = "dept_name")
	private String deptName;

	@Column(name = "demension_type", nullable = true)
	private Integer dimensionType;

}
