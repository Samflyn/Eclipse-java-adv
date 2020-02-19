package com.sailotech.mcap.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sailotech.mcap.dto.EventResponse;
import com.sailotech.mcap.dto.InvoiceHeaderDto;
import com.sailotech.mcap.exception.DataValidationException;
import com.sailotech.mcap.service.InvoiceProcessService;
import com.sailotech.mcap.util.MesserApAutomationConstants;
import com.sailotech.mcap.util.MesserApAutomationUtil;

@RestController

public class InvoiceProcessController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceProcessController.class);

	@Autowired
	MesserApAutomationUtil mcApUtil;

	@Autowired
	InvoiceProcessService invoiceProcessService;
	@Autowired
	MesserApAutomationConstants mcapConstants;
	private Object principal;

	@PostMapping(value = "/saveinvoice")
	@ResponseBody
	public String saveInvoice(@RequestBody InvoiceHeaderDto invoiceHeaderDto) throws DataValidationException {
		LOGGER.info("Entered into InvoiceProcessController saveInvoice {} ", invoiceHeaderDto);
		InvoiceHeaderDto header = invoiceProcessService.saveInvoice(invoiceHeaderDto);
		return mcApUtil.convertPojoToJson(header);
	}

	@GetMapping(value = "/invoiceBytxn")

	public EventResponse getInvoicesByTxnAndStatus(@RequestParam String transactionType,
			@RequestParam Integer workflowStatus) throws DataValidationException {
		LOGGER.info("fetching all invoices based on  workflow status#  {} ", workflowStatus);
		List<InvoiceHeaderDto> headerlist = invoiceProcessService.getInvoicesByTxnAndStatus(transactionType,
				workflowStatus);
		if (!headerlist.isEmpty())
			return mcApUtil.prepareSuccessResponse(mcapConstants.SUCCESS_CD, mcapConstants.SUCCESS_MSG, headerlist);
		else
			return mcApUtil.preareErrorResponse(mcapConstants.ERROR_CD, mcapConstants.ERROR_MSG);

	}

	@PutMapping(value = "/erpInvoiceBooking")
	public EventResponse erpInvoiceBooking(@RequestParam Integer headerId) throws DataValidationException {
		LOGGER.info("Invoice Booking in ERP System headerID# {} ", headerId);
		EventResponse eventRes = invoiceProcessService.erpInvoiceBooking(headerId);
		if (eventRes.getEventStatus().equals("SUCCESS")) {
			// update workflow status
			invoiceProcessService.updateWorkFlowStatus(headerId, 2);
			return eventRes;

		} else {
			// return to client with reponse message
			return eventRes;
		}

	}

	/*
	 * @GetMapping(value = "/getinvoice")
	 * 
	 * @ResponseBody public String getInvoice(HttpServletRequest request,
	 * HttpSession session) throws DataValidationException { return
	 * mcApUtil.convertPojoToJson(new InvoiceHeaderDto()); }
	 */

	/*
	 * @PostMapping(value = "/saveCostInvoice")
	 * 
	 * @ResponseBody public String saveInvoice(@RequestBody CostInvoiceHeaderDto
	 * costInvoiceHeaderDto, HttpServletRequest request, HttpSession session) throws
	 * DataValidationException {
	 * LOGGER.info("Entered into CostInvoiceProcessController saveInvoice {} ",
	 * costInvoiceHeaderDto); String loginData =
	 * request.getHeader(SVMPConstants.LOGIN_DETAILS); LoginDetails loginDetails =
	 * messerUtil.convertTypeReference(loginData, LoginDetails.class);
	 * 
	 * List<String> validationMessages =
	 * validationUtil.validateCostInvoice(costInvoiceHeaderDto); if
	 * (!validationMessages.isEmpty()) { throw new
	 * DataValidationException(SVMPConstants.SVMP_DATA_VALIDATION_ERROR,
	 * validationMessages.toString().trim().replace("[", "").replace("]", "")); }
	 * 
	 * CostInvoiceHeaderDto header =
	 * invoiceProcessService.saveCostInvoice(costInvoiceHeaderDto, loginDetails);
	 * 
	 * return messerUtil.convertPojoToJson(header); }
	 */

}
