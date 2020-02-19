package com.sailotech.mcap.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sailotech.mcap.dto.EventResponse;
import com.sailotech.mcap.dto.InvoiceHeaderDto;
import com.sailotech.mcap.dto.WorkFlowStatus;
import com.sailotech.mcap.entity.InvoiceHeader;
import com.sailotech.mcap.repository.InvoiceHeaderRepository;
import com.sailotech.mcap.repository.UserRepository;
import com.sailotech.mcap.service.InvoiceProcessService;
import com.sailotech.mcap.util.MesserApAutomationUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class InvoiceProcessServiceImpl implements InvoiceProcessService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceProcessServiceImpl.class);

	@Autowired
	private InvoiceHeaderRepository invoiceHeaderRepo;

	@Autowired
	MesserApAutomationUtil messerUtil;

	@Autowired
	UserRepository userRepository;

	@Autowired
	InvoiceProcessService invoiceProcessService;

	@Autowired
	ModelMapper mapper;

	public InvoiceHeaderDto saveInvoice(InvoiceHeaderDto invoiceHeaderDto) {

		InvoiceHeader invoiceHeader = messerUtil.convertInvoiceHeaderDtoToDao(invoiceHeaderDto);
		if (invoiceHeader.getHeaderId() != null) {
			invoiceHeader.setUpdate();

		} else {
			invoiceHeader.setSave();
			invoiceHeader.setUserId(messerUtil.getUserId());
			// invoice is created from automation service
			invoiceHeader.setChannelType("Manual");
			// invoiceHeader.setWorkFlowStatus(WorkFlowStatus.SAVED.value());
		}
		return messerUtil.convertInvoiceHeaderDaoToDto(invoiceHeaderRepo.save(invoiceHeader));
	}

	@Override
	public EventResponse erpInvoiceBooking(Integer headerId) {
		Optional<InvoiceHeader> invoiceHeader = invoiceHeaderRepo.findByHeaderIdAndWorkFlowStatus(headerId, 1);

		if (invoiceHeader.isPresent()) {
			// LN Integraion logic
			return messerUtil.preareSuccessResponse("ST-000", "Invoice booking completed");

		} else {
			return messerUtil.preareErrorResponse("ST-001", "Details are not available");
		}

	}

	@Override
	public void updateWorkFlowStatus(Integer headerId, Integer workFlowStatus) {
		Optional<InvoiceHeader> invoiceHeader = invoiceHeaderRepo.findById(headerId);
		if (invoiceHeader.isPresent()) {
			InvoiceHeader header = invoiceHeader.get();
			header.setWorkFlowStatus(workFlowStatus);
			invoiceHeaderRepo.save(header);

		} else {

		}

	}

	@Override
	public List<InvoiceHeaderDto> getInvoicesByTxnAndStatus(String transactionType, Integer workflowStatus) {
		Optional<List<InvoiceHeader>> invoiceHeader = invoiceHeaderRepo
				.findByTransactionTypeAndWorkFlowStatus(transactionType, workflowStatus);
		List<InvoiceHeaderDto> invoiceHeaderDtoList = null;
		if (invoiceHeader.isPresent())

			invoiceHeaderDtoList = mapper.map(invoiceHeader.get(), new TypeToken<List<InvoiceHeaderDto>>() {
			}.getType());

		return invoiceHeaderDtoList;
	}

}
