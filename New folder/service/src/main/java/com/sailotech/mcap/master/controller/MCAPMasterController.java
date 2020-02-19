package com.sailotech.mcap.master.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sailotech.mcap.dto.CountryMasterDto;
import com.sailotech.mcap.dto.EmailConfigurationDto;
import com.sailotech.mcap.dto.ErrorMasterDto;
import com.sailotech.mcap.dto.ItemMappingDto;
import com.sailotech.mcap.dto.ItemMasterDto;
import com.sailotech.mcap.dto.RoleMasterDto;
import com.sailotech.mcap.dto.StateMasterDto;
import com.sailotech.mcap.dto.UQCMappingDto;
import com.sailotech.mcap.dto.UQCMasterDto;
import com.sailotech.mcap.exception.DataValidationException;
import com.sailotech.mcap.master.service.CountryMasterService;
import com.sailotech.mcap.master.service.DimensionService;
import com.sailotech.mcap.master.service.EmailService;
import com.sailotech.mcap.master.service.ErrorMasterService;
import com.sailotech.mcap.master.service.ItemMappingService;
import com.sailotech.mcap.master.service.ItemMasterService;
import com.sailotech.mcap.master.service.LedgerAccountService;
import com.sailotech.mcap.master.service.RoleMasterService;
import com.sailotech.mcap.master.service.StateMasterService;
import com.sailotech.mcap.master.service.UqcMappingService;
import com.sailotech.mcap.master.service.UqcMasterService;
import com.sailotech.mcap.service.InvoiceProcessService;
import com.sailotech.mcap.util.MesserApAutomationConstants;
import com.sailotech.mcap.util.MesserApAutomationUtil;

/**
 * @author nagendra.babu
 */

@RestController
public class MCAPMasterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MCAPMasterController.class);

	@Autowired
	RoleMasterService roleMasterService;

	@Autowired
	CountryMasterService countryMasterService;

	@Autowired
	StateMasterService stateMasterService;

	@Autowired
	ItemMasterService gstItemMasterService;

	@Autowired
	UqcMasterService uqcMasterService;

	@Autowired
	InvoiceProcessService invoiceProcessService;

	@Autowired
	MesserApAutomationUtil messerApAutomationUtil;

	@Autowired
	UqcMappingService uqcMappingService;

	@Autowired
	ItemMappingService itemMappingService;

	@Autowired
	ErrorMasterService errorMasterService;

	@Autowired
	LedgerAccountService ledgerAccountService;

	@Autowired
	DimensionService dimensionService;

	@Autowired
	EmailService emailService;

	@PostMapping(value = "/saveItemMaster")
	public Map<String, String> saveItemMaster(@RequestBody ItemMasterDto gstItemMasterDto, HttpServletRequest request,
			HttpSession session) throws DataValidationException {
		LOGGER.info("Entered into InvoiceProcessController saveItemMaster {}", gstItemMasterDto);
		gstItemMasterService.saveItemMaster(gstItemMasterDto);
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put(MesserApAutomationConstants.MESSAGE, "Item saved successfully");
		dataMap.put(MesserApAutomationConstants.STATUS, MesserApAutomationConstants.SUCCESS_STATUS);
		return dataMap;
	}

	@PostMapping("/saveExcelItems")
	@ResponseBody
	public String processItemsExcel(@RequestBody List<ItemMasterDto> itemMasterDto, HttpServletRequest request)
			throws DataValidationException {
		List<ItemMasterDto> listOfItems = itemMasterDto;
		for (ItemMasterDto loi : listOfItems) {
			gstItemMasterService.saveItemMaster(loi);
		}
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put(MesserApAutomationConstants.MESSAGE, "Items uploaded successfully");
		dataMap.put(MesserApAutomationConstants.STATUS, MesserApAutomationConstants.SUCCESS_STATUS);
		return messerApAutomationUtil.convertPojoToJson(dataMap);
	}

	@PostMapping("/saveExcelItemMapping")
	@ResponseBody
	public String saveExcelItemMapping(@RequestBody List<ItemMappingDto> itemMappingDto, HttpServletRequest request)
			throws DataValidationException {
		List<ItemMappingDto> listOfitems = itemMappingDto;
		for (ItemMappingDto loi : listOfitems) {
			itemMappingService.saveItemMapping(loi);
		}
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put(MesserApAutomationConstants.MESSAGE, "Item Mapping saved successfully");
		dataMap.put(MesserApAutomationConstants.STATUS, MesserApAutomationConstants.SUCCESS_STATUS);
		return messerApAutomationUtil.convertPojoToJson(dataMap);
	}

	@PostMapping("/saveUserRoles")
	@ResponseBody
	public String saveUserRoles(@RequestBody RoleMasterDto roleMasterDto, HttpServletRequest request,
			HttpSession session) throws DataValidationException {
		LOGGER.info("Entered into MCAPMasterController saveUserRoles {} ", roleMasterDto);
		roleMasterService.saveUserRoles(roleMasterDto);
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put(MesserApAutomationConstants.MESSAGE, "Roles saved successfully");
		dataMap.put(MesserApAutomationConstants.STATUS, MesserApAutomationConstants.SUCCESS_STATUS);
		return messerApAutomationUtil.convertPojoToJson(dataMap);

	}

	@GetMapping(value = "/getAllUserRoles")
	@ResponseBody
	public String getAllUserRoles() {
		LOGGER.info("Entered into MCAPMasterController getAllUserRoles{} ", "getting Roles");
		return roleMasterService.getAllUserRoles();
	}

	@DeleteMapping(value = "/deleteRole/{roleId}")
	public String deleteUserRole(@PathVariable("roleId") Integer roleId) {
		LOGGER.info("Entered into MCAPMasterController deleteTenant with roleId  {} ", roleId);
		roleMasterService.deleteByRoleId(roleId);
		return messerApAutomationUtil.convertPojoToJson("Role deleted successfully");
	}

	@PostMapping("/saveCountry")
	@ResponseBody
	public String saveCountryDetails(@RequestBody CountryMasterDto countryMasterDto, HttpServletRequest request,
			HttpSession session) throws DataValidationException {
		LOGGER.info("Entered into MCAPMasterController saveCountryDetails {} ", countryMasterDto);
		countryMasterService.saveCountryDetails(countryMasterDto);
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put(MesserApAutomationConstants.MESSAGE, "Country saved successfully");
		dataMap.put(MesserApAutomationConstants.STATUS, MesserApAutomationConstants.SUCCESS_STATUS);
		return messerApAutomationUtil.convertPojoToJson(dataMap);
	}

	@GetMapping(value = "/getAllCountries")
	@ResponseBody
	public String getAllCountries() {
		LOGGER.info("Entered into MCAPMasterController getAllCountries{} ", "getting countries");
		return countryMasterService.getAllCountryDetails();
	}

	@DeleteMapping(value = "/deleteCounty/{countryId}")
	public String deleteCounty(@PathVariable("countryId") Integer countryId) {
		LOGGER.info("Entered into MCAPMasterController deleteTenant with countryId  {} ", countryId);
		countryMasterService.deleteByCountryId(countryId);
		return messerApAutomationUtil.convertPojoToJson("Country deleted successfully");
	}

	@PostMapping(value = "/savestate")
	public String saveState(@RequestBody StateMasterDto stateMasterDto, HttpServletRequest request, HttpSession session)
			throws DataValidationException {
		LOGGER.info("Entered into MCAPMasterController saveState {} ", stateMasterDto);
		stateMasterService.saveState(stateMasterDto);
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put(MesserApAutomationConstants.MESSAGE, "State saved successfully");
		dataMap.put(MesserApAutomationConstants.STATUS, MesserApAutomationConstants.SUCCESS_STATUS);
		return messerApAutomationUtil.convertPojoToJson(dataMap);
	}

	@GetMapping(value = "/getAllStates")
	@ResponseBody
	public String getAllStates() {
		LOGGER.info("Entered into MCAPMasterController getAllStates ");
		return stateMasterService.getAllStates();
	}

	@DeleteMapping(value = "/deleteState/{stateId}")
	public String deleteState(@PathVariable("stateId") Integer stateId) {
		LOGGER.info("Entered into MCAPMasterController deleteTenant with stateId  {} ", stateId);
		stateMasterService.deleteByStateId(stateId);
		return messerApAutomationUtil.convertPojoToJson("State deleted successfully");
	}

	@GetMapping(value = "/getItemMaster")
	@ResponseBody
	public String getItemMaster() {
		LOGGER.info("Entered into MCAPMasterController getItemMaster ");
		return gstItemMasterService.getItemMaster();
	}

	@DeleteMapping(value = "/deleteItem/{itemId}")
	@ResponseBody
	public String deleteItem(@PathVariable("itemId") Integer itemId) {
		LOGGER.info("Entered into MCAPMasterController deleteItem ");
		String message = gstItemMasterService.deletePartner(itemId);
		return messerApAutomationUtil.convertPojoToJson(message);
	}

	@GetMapping(value = "/getItems")
	@ResponseBody
	public List<ItemMasterDto> getItems(@RequestParam("companyId") Integer companyId,
			@RequestHeader("loginDetails") String loginData) {
		LOGGER.info("Entered into Entered into MCAPMasterController getItems with companyId {} ", companyId);
		return gstItemMasterService.getAllItemsByCompanyId(companyId);
	}

	@PostMapping(value = "/saveuqc")
	public String saveuqc(@RequestBody UQCMasterDto uQCMasterDto, HttpServletRequest request, HttpSession session)
			throws DataValidationException {
		LOGGER.info("Entered into MCAPMasterController saveuqc {} ", uQCMasterDto);
		uqcMasterService.saveUqc(uQCMasterDto);
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put(MesserApAutomationConstants.MESSAGE, "UQC saved successfully");
		dataMap.put(MesserApAutomationConstants.STATUS, MesserApAutomationConstants.SUCCESS_STATUS);
		return messerApAutomationUtil.convertPojoToJson(dataMap);
	}

	@GetMapping(value = "/getUqcList")
	public String getUqcListByTenant(@RequestParam("companyId") Integer companyId) {
		LOGGER.info("Entered into MCAPMasterController getUqcList with companyId {} ", companyId);
		return uqcMasterService.getUqcListByCompanyId(companyId);
	}

	@DeleteMapping(value = "/deleteUqc/{uqcMasterId}")
	public String deleteUqc(@PathVariable("uqcMasterId") Integer uqcMasterId) {
		LOGGER.info("Entered into MCAPMasterController deleteUqc with uqcMasterId  {} ", uqcMasterId);
		uqcMasterService.deleteUqcById(uqcMasterId);
		return messerApAutomationUtil.convertPojoToJson("Uqc deleted successfully");
	}

	@PostMapping(value = "/saveUqcMapping")
	public String saveUqcMapping(@RequestBody UQCMappingDto uqcMappingDto, HttpServletRequest request,
			HttpSession session) throws DataValidationException {
		LOGGER.info("Entered into MCAPMasterController saveUqcMapping {} ", uqcMappingDto);
		uqcMappingService.saveUqcMapping(uqcMappingDto);
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put(MesserApAutomationConstants.MESSAGE, "UQC Mapping saved successfully");
		dataMap.put(MesserApAutomationConstants.STATUS, MesserApAutomationConstants.SUCCESS_STATUS);
		return messerApAutomationUtil.convertPojoToJson(dataMap);
	}

	@PostMapping("/saveExcelUqc")
	@ResponseBody
	public String processExcelUqc(@RequestBody List<UQCMasterDto> uQCMasterDto, HttpServletRequest request)
			throws DataValidationException {
		List<UQCMasterDto> listOfUqc = uQCMasterDto;
		for (UQCMasterDto lou : listOfUqc) {
			uqcMasterService.saveUqc(lou);
		}
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put(MesserApAutomationConstants.MESSAGE, "UQC uploaded successfully");
		dataMap.put(MesserApAutomationConstants.STATUS, MesserApAutomationConstants.SUCCESS_STATUS);
		return messerApAutomationUtil.convertPojoToJson(dataMap);

	}

	@PostMapping("/saveExcelUqcMapping")
	@ResponseBody
	public String processUqcMappingExcel(@RequestBody List<UQCMappingDto> uQCMappingDto, HttpServletRequest request)
			throws DataValidationException {
		List<UQCMappingDto> listOfuqc = uQCMappingDto;
		for (UQCMappingDto loc : listOfuqc) {
			uqcMappingService.saveUqcMapping(loc);
		}
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put(MesserApAutomationConstants.MESSAGE, "UQC Mappings uploaded successfully");
		dataMap.put(MesserApAutomationConstants.STATUS, MesserApAutomationConstants.SUCCESS_STATUS);
		return messerApAutomationUtil.convertPojoToJson(dataMap);
	}

	@GetMapping(value = "/getUqcMapping/{tenantId}")
	@ResponseBody
	public String getUqcMapping(@PathVariable("tenantId") Integer tenantId) {
		LOGGER.info("Entered into MCAPMasterController getUqcMapping{}", tenantId);
		return uqcMappingService.getUqcMapping(tenantId);
	}

	@DeleteMapping(value = "/deleteUqcMapping/{uqcMappingId}")
	public String deleteUqcMapping(@PathVariable("uqcMappingId") Integer uqcMappingId) {
		LOGGER.info("Entered into MCAPMasterController deletePemission with deleteUqcMapping {} ", uqcMappingId);
		uqcMappingService.deleteUqcMapping(uqcMappingId);
		return messerApAutomationUtil.convertPojoToJson("UQC Mapping deleted successfully");
	}

	@PostMapping(value = "/saveItemMapping")
	public String saveItemMapping(@RequestBody ItemMappingDto itemMappingDto, HttpServletRequest request,
			HttpSession session) throws DataValidationException {
		LOGGER.info("Entered into MCAPMasterController saveItemMapping {} ", itemMappingDto);
		itemMappingService.saveItemMapping(itemMappingDto);
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put(MesserApAutomationConstants.MESSAGE, "Item Mapping saved successfully");
		dataMap.put(MesserApAutomationConstants.STATUS, MesserApAutomationConstants.SUCCESS_STATUS);
		return messerApAutomationUtil.convertPojoToJson(dataMap);
	}

	@GetMapping(value = "/getItemMapping/{tenantId}")
	@ResponseBody
	public String getItemMapping(@PathVariable("tenantId") Integer tenantId) {
		LOGGER.info("Entered into MCAPMasterController getItemMapping with tenantId{}", tenantId);
		return itemMappingService.getItemMapping(tenantId);
	}

	@DeleteMapping(value = "/deleteItemMapping/{itemMappingId}")
	public String deleteItemMapping(@PathVariable("itemMappingId") Integer itemMappingId) {
		LOGGER.info("Entered into MCAPMasterController deletePemission with deleteItemMapping {} ", itemMappingId);
		itemMappingService.deleteItemMapping(itemMappingId);
		return messerApAutomationUtil.convertPojoToJson("Item Mapping deleted successfully");
	}

	@PostMapping(value = "/saveErrorMaster")
	public String saveErrorMaster(@RequestBody ErrorMasterDto errorMasterDto, HttpServletRequest request,
			HttpSession session) {
		LOGGER.info("Entered into MCAPMasterController saveErrorMaster {} ", errorMasterDto);
		errorMasterService.saveItemMapping(errorMasterDto);
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put(MesserApAutomationConstants.MESSAGE, "Error Message  saved successfully");
		dataMap.put(MesserApAutomationConstants.STATUS, MesserApAutomationConstants.SUCCESS_STATUS);
		return messerApAutomationUtil.convertPojoToJson(dataMap);
	}

	@GetMapping(value = "/getErrorMasters")
	@ResponseBody
	public String getErrorMasters() {
		LOGGER.info("Entered into MCAPMasterController getErrorMasters ");
		return errorMasterService.getErrorMasters();
	}

	@DeleteMapping(value = "/deleteErrorMaster/{errorId}")
	public String deleteErrorMaster(@PathVariable("errorId") Integer errorId) {
		LOGGER.info("Entered into MCAPMasterController deletePemission with deleteErrorMaster {} ", errorId);
		errorMasterService.deleteErrorMaster(errorId);
		return messerApAutomationUtil.convertPojoToJson("Item Mapping deleted successfully");
	}

	@GetMapping(value = "/getDimensions/{ledgerAccount}")
	public String getDimensions(@PathVariable("ledgerAccount") String ledgerAcount) {
		LOGGER.info("Entered into MCAPMasterController getDimensions {} ", ledgerAcount);
		return dimensionService.getDimensions(ledgerAcount);
	}

	@GetMapping(value = "/getLedgerAccount")
	public String getLedgerAccount(@RequestParam("companyId") Integer companyId) {
		LOGGER.info("Entered into MCAPMasterController getLedgerAccount by company {} ", companyId);
		return ledgerAccountService.getLedgerAccountByCompanyId(companyId);
	}

	@PostMapping(value = "/saveMailConfigs")
	public String saveMailConfigs(@RequestBody EmailConfigurationDto mailConfigurationsMasterDto,
			HttpServletRequest request) {
		LOGGER.info("Entered into EmailServiceController {} ", mailConfigurationsMasterDto);
		return emailService.saveMailConfigs(mailConfigurationsMasterDto);
	}

	@GetMapping(value = "/getAllEmailConfigs")
	public String getAllEmailConfigs() {
		LOGGER.info("Entered into EmailServiceController getAllEmailConfigs ");
		return emailService.getAllEmailConfigs();
	}

	@DeleteMapping(value = "/deleteEmailConfig/{mailConfigId}")
	public String deleteEmailConfigMapping(@PathVariable("mailConfigId") Integer mailConfigId) {
		LOGGER.info("Entered into EmailServiceController deletePemission with deleteEmailConfigMapping {} ",
				mailConfigId);
		emailService.deleteUqcMapping(mailConfigId);
		return messerApAutomationUtil.convertPojoToJson("Email Config deleted successfully");
	}
}
