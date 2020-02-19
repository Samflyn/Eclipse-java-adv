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
@Table(name = "state_codes")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@EqualsAndHashCode(of = "stateId", callSuper = false)
public class StateMaster extends BaseDao implements Serializable {

	private static final long serialVersionUID = -7759133400376252308L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "state_id")
	private Integer stateId;

	@Column(name = "state_code")
	private String stateCode;

	@Column(name = "state_name", nullable = true)
	private String stateName;
}
