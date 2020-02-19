package com.sailotech.mcap.dto;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author nagendra.babu
 */
public enum WorkFlowStatus {

	SAVED(1), REGISTERED(2), GRN_PENDING(3), GRN_AVAILABLE(4), MATCHED(5), MISMATCHED(6),
	PD_APPROVAL_PENDING(7), PD_APPROVED(8), LN_APPROVED(9), PAYMENT(10);

	private Integer value;

	WorkFlowStatus(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return value;
	}

	public String getNameByValue(Integer  value) {
		Optional<WorkFlowStatus> workflowStatus = Arrays.stream(WorkFlowStatus.values())
				.filter(e -> e.value.equals(value)).findAny();
		if (workflowStatus.isPresent()) {
			return workflowStatus.get().name();
		}
		return null;
	}
}
