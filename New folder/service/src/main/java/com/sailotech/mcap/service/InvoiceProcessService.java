package com.sailotech.mcap.service;

import java.util.List;

import com.sailotech.mcap.dto.EventResponse;
import com.sailotech.mcap.dto.InvoiceHeaderDto;

public interface InvoiceProcessService {
	
	public InvoiceHeaderDto saveInvoice(InvoiceHeaderDto invoiceHeaderDto);
	
	        EventResponse erpInvoiceBooking(Integer headerId);

			public void updateWorkFlowStatus(Integer headerId, Integer workFlowStatus);

			public List<InvoiceHeaderDto> getInvoicesByTxnAndStatus(String transactionType, Integer workflowStatus);
}
