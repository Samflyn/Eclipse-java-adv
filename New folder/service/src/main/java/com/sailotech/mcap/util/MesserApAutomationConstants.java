package com.sailotech.mcap.util;

import org.springframework.stereotype.Component;

@Component
public class MesserApAutomationConstants {

	public static final String CUSTOMER = "CUSTOMER";
	public static final String NLINE = "nline";
	public static final String OLINE = "oline";
	public static final String STATUS_OPEN = "OPEN";
	public static final String STATUS_INPROGRESS = "INPROGRESS";
	public static final String STATUS_FAILED = "FAILED";
	public static final String STATUS_COMPLETED = "COMPLETED";
	public static final String RAW_FILES = "Raw";
	public static final String ARC_FILES = "Success";
	public static final String FAILED_FILES = "Failed";
	public static final String FILE_EXTENSION = "pdf";
	public static final boolean SCHEDULE_EXPORT = true;
	public static final String EXPORT_THROUGH_SCHEDULE = "Schedular Export";
	public static final String EXPORT_THROUGH_SCREEN = "Generate Report";
	public static final String STATUS = "status";
	public static final String SVMP_DATA_VALIDATION_ERROR="SVMP_DATA_VALIDATION_ERROR";
	public static final String SUCCESS_STATUS="Success";
	public static final String ERP_STATUS="Status";
	public static final String RESPONSE="No response from Server LN ERP";
	public static final String RESPONSE_ERP=" Response from LN ERP : ";
	public static final String MESSAGE="message";
	public static final String ERROR="error";
	public static final String LOGIN_DETAILS="loginDetails";
	public static final String RECORDS_FILTERED= "recordsFiltered";
	public static final String VENDOR = "VENDOR";
	public static final String OTP_REQUEST = "OTPREQUEST";
	public static final String COSTINVOICE = "Cost Invoice";
	protected static final String[] CUSTOMER_INVOICE_HEADERS = {"Vendor Name","Vendor Registered ID","Invoice Number","Invoice Date","PO Number","Due Date","Item Description","Item Rate","Line Amount","Invoice Amount","Status"};
	protected static final String[] CUSTOMER_COST_INVOICE_HEADERS = {"Vendor Name","Vendor Registered ID","Invoice Number","Invoice Date","Due Date","Item Description","Item Rate","Line Amount","Invoice Amount","Status"};
	protected static final String[] VENDOR_INVOICE_HEADERS = {"Client Name","Client Registered ID","Invoice Number","Invoice Date","PO Number","Due Date","Item Description","Item Rate","Line Amount","Invoice Amount","Status"};
	static final String[] VENDOR_COST_INVOICE_HEADERS = {"Client Name","Client Registered ID","Invoice Number","Invoice Date","Due Date","Item Description","Item Rate","Line Amount","Invoice Amount","Status"};
	protected static final String[] customerExcel = {"Vendor Name","Invoice Number","Invoice Date","PO Number","Due Date","Due Amount"};
	protected static final String[] vendorExcel = {"Invoice Number","Invoice Date","PO Number","Due Date","Due Amount"};
	protected static final String[] BULK_INVOICE_HEADERS = {"Company GSTIN","Company Name","Company Address","BP Name","BP Address","BP State","Shipping Address","Invoice Number","Invoice Date","PO Number","Due Date","Invoice Value","HSN","UQC","Rate","Taxable Value","Tax Rate","CGST","SGST","IGST","CESS","Line Value","Discount"};
	protected static final String[] CUSTOMER_PURCHASE_INVOICE_REPORT = {"Vendor Name","Vendor Registered ID","Invoice Number","Invoice Date","PO Number","Due Date","IBAN","Delivery Terms","Payment Terms","Good Receipt Note","Item Description","Item Type","HSN/SAC","UQC","Quantity","Item Rate","Taxable Value","Tax Rate","CGST","SGST","IGST","CESS","VAT","Vat Amount","Freight","Packing","Currency","Line Amount","Status"};
	protected static final String[] CUSTOMER_COST_INVOICE_REPORT = {"Vendor Name","Vendor Registered ID","Invoice Number","Invoice Date","Due Date","IBAN","Delivery Terms","Payment Terms","Good Receipt Note","Item Description","Item Type","HSN/SAC","UQC","Quantity","Item Rate","Taxable Value","Tax Rate","CGST","SGST","IGST","CESS","VAT","Vat Amount","Freight","Packing","Currency","Line Amount","Status"};

	public static final String LICENSE_ACTIVE = "Active";
	public static final String LICENSE_INACTIVE = "InActive";
	public static final String ERROR_MESSAGE = "errorMessage";
	//public static final String ERROR_CODE = "errorCode";
	
	public static final String SUCCESS_CD = "ST-000";
	public static final String ERROR_CD = "ST-001";
	public static final String SUCCESS_MSG = "Details are available";
	public static final String ERROR_MSG= "Details are not available";
	public static String[] getCustomerexcel() {
		return customerExcel;
	}

	public static String[] getVendorexcel() {
		return vendorExcel;
	}

	private MesserApAutomationConstants() {
		// This is a constants class no need to have constructor
	}

	public static String getCustomer() {
		return CUSTOMER;
	}

	public static String getVendor() {
		return VENDOR;
	}

	public static String getNline() {
		return NLINE;
	}

	public static String getOline() {
		return OLINE;
	}

	public static String getStatusOpen() {
		return STATUS_OPEN;
	}

	public static String getStatusInprogress() {
		return STATUS_INPROGRESS;
	}

	public static String getStatusFailed() {
		return STATUS_FAILED;
	}

	public static String getStatusCompleted() {
		return STATUS_COMPLETED;
	}

	public static String getRawFiles() {
		return RAW_FILES;
	}

	public static String getArcFiles() {
		return ARC_FILES;
	}

	public static String getFailedFiles() {
		return FAILED_FILES;
	}

	public static String getFileExtension() {
		return FILE_EXTENSION;
	}

	public static boolean isScheduleExport() {
		return SCHEDULE_EXPORT;
	}

	public static String getExportThroughSchedule() {
		return EXPORT_THROUGH_SCHEDULE;
	}

	public static String getExportThroughScreen() {
		return EXPORT_THROUGH_SCREEN;
	}

	public static String getStatus() {
		return STATUS;
	}

	public static String getSvmpDataValidationError() {
		return SVMP_DATA_VALIDATION_ERROR;
	}

	public static String getSuccessStatus() {
		return SUCCESS_STATUS;
	}

	public static String getMessage() {
		return MESSAGE;
	}

	public static String getLoginDetails() {
		return LOGIN_DETAILS;
	}

	public static String getRecordsFiltered() {
		return RECORDS_FILTERED;
	}

	public static String getOtpRequest() {
		return OTP_REQUEST;
	}

	public static String[] getCustomerInvoiceHeaders() {
		return CUSTOMER_INVOICE_HEADERS;
	}

	public static String[] getVendorInvoiceHeaders() {
		return VENDOR_INVOICE_HEADERS;
	}

	public static String[] getBulkInvoiceHeaders() {
		return BULK_INVOICE_HEADERS;
	}
}
