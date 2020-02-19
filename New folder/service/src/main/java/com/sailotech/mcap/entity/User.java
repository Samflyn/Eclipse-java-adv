package com.sailotech.mcap.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author nagendra.babu
 *
 * @version 1.0
 *
 */
@Entity
@Table(name = "company_user")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Setter
@EqualsAndHashCode(of = "userId", callSuper = false)
public class User extends BaseDao implements UserDetails, Serializable {

	private static final long serialVersionUID = -7174791044981739916L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	private Integer userId;

	@Column(name = "full_name", nullable = true)
	private String fullName;

	@Column(name = "user_name", unique = true, nullable = false)
	private String username;

	@Column(name = "email_id", unique = true, nullable = false)
	private String emailId;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "otp", nullable = true)
	private String otp;

	@Column(name = "otp_generated_time", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date otpGeneratedTime;

	@Column(name = "otp_expiry_time", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date otpExpiryTime;

	@Column(name = "account_expired")
	private boolean accountExpired;

	@Column(name = "account_locked")
	private boolean accountLocked;

	@Column(name = "credentials_expired")
	private boolean credentialsExpired;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "designation", nullable = true)
	private String designation;

	@Column(name = "pic", columnDefinition = "blob")
	private byte[] pic;

	@Column(name = "is_pic_uploaded")
	private boolean picUploaded;

	@Column(name = "address_line1", nullable = true)
	private String addressLine1;

	@Column(name = "address_line2", nullable = true)
	private String addressLine2;

	@Column(name = "city", nullable = true)
	private String city;

	@Column(name = "pin_code", nullable = true)
	private String pinCode;

	@Column(name = "landline", nullable = true)
	private String landLine;

	@Column(name = "mobile", nullable = true)
	private String mobile;

	@ManyToOne(optional = false)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	@OneToOne
	@JoinColumn(name = "state_id", nullable = true)
	private StateMaster stateMaster;

	@OneToOne
	@JoinColumn(name = "country_id", nullable = true)
	private CountryMaster countryMaster;

	@OneToOne
	@JoinColumn(name = "role_id", nullable = true)
	private RoleMaster roleMaster;

	@OneToOne
	@JoinColumn(name = "dept_id", nullable = true)
	private Department department;

	@Column(name = "invalid_login_attempts")
	private Integer invalidLoginAttempts;

	@Column(name = "last_password_update", nullable = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastPasswordUpdate;

	@Column(name = "manager_id")
	private Integer managerId;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USERS_AUTHORITIES", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID"))
	@OrderBy
	private Collection<Authority> authorities;

	@Override
	public boolean isAccountNonExpired() {
		return !isAccountExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return !isAccountLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !isCredentialsExpired();
	}
}
