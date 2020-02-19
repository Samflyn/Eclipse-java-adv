/**
 * 
 */
package com.sailotech.mcap.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author dhanunjaya.potteti
 *
 */
@Component
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(Include.NON_NULL)
public class EventResponse {
	private String eventBusinessId;
	private String eventStatus;
	private Object eventData;
	private String eventCode;
	private String eventMessage;
	
	

}
