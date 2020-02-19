package com.sailotech.mcap.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sailotech.mcap.dto.CompanyDto;
import com.sailotech.mcap.dto.CountryMasterDto;
import com.sailotech.mcap.dto.EventResponse;
import com.sailotech.mcap.dto.InvoiceHeaderDto;
import com.sailotech.mcap.dto.InvoiceLinesDto;
import com.sailotech.mcap.dto.ItemMappingDto;
import com.sailotech.mcap.dto.ItemMasterDto;
import com.sailotech.mcap.dto.RoleMasterDto;
import com.sailotech.mcap.dto.StateMasterDto;
import com.sailotech.mcap.dto.UQCMappingDto;
import com.sailotech.mcap.dto.UQCMasterDto;
import com.sailotech.mcap.dto.UserDto;
import com.sailotech.mcap.entity.Company;
import com.sailotech.mcap.entity.CountryMaster;
import com.sailotech.mcap.entity.InvoiceHeader;
import com.sailotech.mcap.entity.InvoiceLines;
import com.sailotech.mcap.entity.ItemMapping;
import com.sailotech.mcap.entity.ItemMaster;
import com.sailotech.mcap.entity.RoleMaster;
import com.sailotech.mcap.entity.StateMaster;
import com.sailotech.mcap.entity.UQCMapping;
import com.sailotech.mcap.entity.User;
import com.sailotech.mcap.exception.ErrorResponse;

@Component
public class MesserApAutomationUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(MesserApAutomationUtil.class);

	@Autowired
	private ApplicationContext applicationContext;

	@SuppressWarnings("unchecked")
	public <T> T copyBeanProperties(Object source, Class<T> requiredType) {
		Object target = applicationContext.getBean(requiredType);
		BeanUtils.copyProperties(source, target);
		if (requiredType != null && requiredType.isInstance(target)) {
			return (T) target;
		}
		return null;
	}

	public String convertPojoToJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return "";
	}

	public String getErrorResponse(String errorMessage, String errorCode) {
		ErrorResponse errorResponse = applicationContext.getBean(ErrorResponse.class);
		errorResponse.setErrorMessage(errorMessage);
		errorResponse.setErrorCode(errorCode);
		return convertPojoToJson(errorResponse);
	}

	public <T> T convertTypeReference(String erpResponse, Class<T> clazz) {
		try {
			return new ObjectMapper().readValue(erpResponse, clazz);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public InvoiceHeaderDto convertInvoiceHeaderDaoToDto(InvoiceHeader source) {
		InvoiceHeaderDto target = applicationContext.getBean(InvoiceHeaderDto.class);
		BeanUtils.copyProperties(source, target);
		List<InvoiceLinesDto> invoiceLines = new ArrayList<>();
		for (InvoiceLines lineSource : source.getItems()) {
			InvoiceLinesDto lineTarget = applicationContext.getBean(InvoiceLinesDto.class);
			BeanUtils.copyProperties(lineSource, lineTarget);
			lineTarget.setHeaderId(source.getHeaderId());
			invoiceLines.add(lineTarget);
		}
		target.setItems(invoiceLines);
		return target;
	}

	public static Integer getUserId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof User)
			return ((User) principal).getUserId();
		return null;
	}

	public Integer getLoggedInUserCompanyId() {
		User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal != null) {
			return principal.getCompany().getCompanyId();
		}
		return null;
	}

	public InvoiceHeader convertInvoiceHeaderDtoToDao(InvoiceHeaderDto invoiceHeaderDto) {
		InvoiceHeader target = applicationContext.getBean(InvoiceHeader.class);
		BeanUtils.copyProperties(invoiceHeaderDto, target);

		List<InvoiceLines> invoiceLines = new ArrayList<>();
		if (null != invoiceHeaderDto.getItems()) {
			for (InvoiceLinesDto lineSource : invoiceHeaderDto.getItems()) {
				InvoiceLines lineTarget = applicationContext.getBean(InvoiceLines.class);
				BeanUtils.copyProperties(lineSource, lineTarget);
				lineTarget.setHeaderId(invoiceHeaderDto.getHeaderId());
				lineTarget.setCreatedOn(Calendar.getInstance().getTime());
				invoiceLines.add(lineTarget);
			}
		}
		target.setItems(invoiceLines);
		return target;
	}

	public EventResponse preareErrorResponse(String code, String msg) {
		EventResponse event = new EventResponse();
		event.setEventStatus("ERROR");
		event.setEventCode(code);
		event.setEventMessage(msg);
		return event;
	}

	public EventResponse prepareSuccessResponse(String code, String msg, Object object) {
		EventResponse event = new EventResponse();
		event.setEventStatus("SUCCESS");
		event.setEventCode(code);
		event.setEventData(object);
		event.setEventMessage(msg);
		return event;
	}

	public EventResponse preareSuccessResponse(String respCode, String respMsg) {
		EventResponse event = new EventResponse();
		event.setEventStatus("SUCCESS");
		event.setEventCode(respCode);
		event.setEventMessage(respMsg);
		return event;
	}

	public ItemMapping convertItemMappingDtoToDao(ItemMappingDto source) {

		ItemMapping target = applicationContext.getBean(ItemMapping.class);
		BeanUtils.copyProperties(source, target);
		ItemMaster itemMaster = applicationContext.getBean(ItemMaster.class);
		if (null != source.getItemMasterDto()) {
			BeanUtils.copyProperties(source.getItemMasterDto(), itemMaster);
		}
		target.setItemMaster(itemMaster);
		return target;
	}

	public ItemMappingDto convertItemMappingDaoToDto(ItemMapping source) {
		ItemMappingDto target = applicationContext.getBean(ItemMappingDto.class);
		BeanUtils.copyProperties(source, target);
		ItemMasterDto itemMasterDto = applicationContext.getBean(ItemMasterDto.class);
		if (null != source.getItemMaster()) {
			BeanUtils.copyProperties(source.getItemMaster(), itemMasterDto);
		}
		target.setItemMasterDto(itemMasterDto);
		return target;
	}

	public UQCMappingDto convertuqcMappingDaoToDto(UQCMapping source) {
		UQCMappingDto target = applicationContext.getBean(UQCMappingDto.class);
		BeanUtils.copyProperties(source, target);
		UQCMasterDto uqcMasterDto = applicationContext.getBean(UQCMasterDto.class);
		if (null != source.getUqcMaster()) {
			BeanUtils.copyProperties(source.getUqcMaster(), uqcMasterDto);
		}
		target.setUqcMasterDto(uqcMasterDto);
		return target;
	}

	public User convertUserDtoToDao(UserDto source) {
		User target = applicationContext.getBean(User.class);
		BeanUtils.copyProperties(source, target);

		Company company = applicationContext.getBean(Company.class);
		if (source.getCompanyDto() != null) {
			company.setCompanyId(source.getCompanyDto().getCompanyId());
			target.setCompany(company);
		}
		CountryMaster countrymaster = applicationContext.getBean(CountryMaster.class);
		if (null != source.getCountryMasterDto().getCountryId()) {
			countrymaster.setCountryId(source.getCountryMasterDto().getCountryId());
			target.setCountryMaster(countrymaster);
		}
		StateMaster stateMaster = applicationContext.getBean(StateMaster.class);
		if (source.getStateDto() != null && source.getStateDto().getStateId() != null) {
			stateMaster.setStateId(source.getStateDto().getStateId());
			target.setStateMaster(stateMaster);
		}
		RoleMaster roleMaster = applicationContext.getBean(RoleMaster.class);
		if (null != source.getRoleMasterDto()) {
			BeanUtils.copyProperties(source.getRoleMasterDto(), roleMaster);
			target.setRoleMaster(roleMaster);
		}
		return target;
	}

	public UserDto convertUserDaoToDto(User source) {
		UserDto target = applicationContext.getBean(UserDto.class);
		BeanUtils.copyProperties(source, target);
		if (source.getCompany() != null) {
			CompanyDto companyDto = this.copyBeanProperties(source.getCompany(), CompanyDto.class);
			target.setCompanyDto(companyDto);
		}
		if (null != source.getCountryMaster()) {
			CountryMasterDto countryMasterDto = this.copyBeanProperties(source.getCountryMaster(),
					CountryMasterDto.class);
			target.setCountryMasterDto(countryMasterDto);
		}
		if (null != source.getStateMaster()) {
			StateMasterDto stateMasterDto = this.copyBeanProperties(source.getStateMaster(), StateMasterDto.class);
			target.setStateDto(stateMasterDto);
		}
		if (null != source.getRoleMaster()) {
			RoleMasterDto roleMasterDto = this.copyBeanProperties(source.getRoleMaster(), RoleMasterDto.class);
			target.setRoleMasterDto(roleMasterDto);
		}
		return target;
	}
}
