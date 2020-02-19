
package com.sailotech.mcap.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sailotech.mcap.util.MesserApAutomationUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * @author nagendra.babu Jul 9, 2018
 */

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@MappedSuperclass
@Getter
@Setter
public class BaseDao implements Serializable {

	private static final long serialVersionUID = 4461284071465485807L;

	@Column(name = "created_by", updatable = false)
	private Integer createdBy;

	@Column(name = "created_on", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Column(name = "last_updated_by")
	private Integer lastUpdatedBy;

	@Column(name = "last_updated_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdatedOn;
	
	public void setSave() {
		this.createdBy = MesserApAutomationUtil.getUserId();
		this.createdOn = Calendar.getInstance().getTime();
	}

	public void setUpdate() {
		this.lastUpdatedBy = MesserApAutomationUtil.getUserId();
		this.lastUpdatedOn = Calendar.getInstance().getTime();
	}

}
