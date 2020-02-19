/**
 * 
 */
package com.sailotech.mcap.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author nagendra.babu Jul 9, 2018
 */
@Getter
@Setter
public class BaseDto implements Serializable {

	private static final long serialVersionUID = -5941053702043817948L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date createdOn;

	private Integer createdBy;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date lastUpdatedOn;

	private Integer lastUpdatedBy;

}
