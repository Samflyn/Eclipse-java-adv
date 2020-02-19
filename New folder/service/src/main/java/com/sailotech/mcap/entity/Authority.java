package com.sailotech.mcap.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Component
@Table(name = "AUTHORITY")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = 1888439595573895472L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Override
	public String getAuthority() {
		return getName();
	}
}
