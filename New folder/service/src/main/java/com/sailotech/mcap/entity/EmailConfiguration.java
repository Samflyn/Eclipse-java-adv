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
@Table(name = "email_configuration")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Setter
@EqualsAndHashCode(of = "mailConfigId", callSuper = false)
public class EmailConfiguration extends BaseDao implements Serializable {

	private static final long serialVersionUID = -410969140898336384L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mail_config_id", unique = true, nullable = false)
	private Integer mailConfigId;

	@Column(name = "protocol", nullable = true)
	private String protocol;

	@Column(name = "mail_host", nullable = true)
	private String host;

	@Column(name = "port", nullable = true)
	private String port;

	@Column(name = "user_name", nullable = true)
	private String userName;

	@Column(name = "password", nullable = true)
	private String password;

	@Column(name = "from_mail", nullable = true)
	private String fromMail;

	@Column(name = "company_id", nullable = false)
	private Integer companyId;

}
