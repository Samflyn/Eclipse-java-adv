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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Component
@Table(name = "error_master")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@EqualsAndHashCode(of = "errorId", callSuper = false)
public class ErrorMaster extends BaseDao implements Serializable {

	private static final long serialVersionUID = -7160959598423699127L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "error_id", unique = true, nullable = false)
	private Integer errorId;

	@Column(name = "error_code", nullable = true)
	private String errorCode;

	@Column(name = "error_desc", nullable = true)
	private String errorDesc;

}
