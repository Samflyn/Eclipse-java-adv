package com.sailotech.mcap.master.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sailotech.mcap.dto.CompanyDto;
import com.sailotech.mcap.entity.Company;
import com.sailotech.mcap.exception.DataValidationException;
import com.sailotech.mcap.master.service.CompanyService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/company")
@Slf4j
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@PostMapping(value = "/create")
	public String create(@RequestBody CompanyDto companyDto, HttpServletRequest request, HttpSession session)
			throws DataValidationException {
		log.info("");
		return companyService.save(companyDto);
	}

	@GetMapping("/getAll")
	public List<Company> getAll() {
		return companyService.getAllCompanyDetails();
	}

	@DeleteMapping("/delete/{companyId}")
	public String delete(@PathVariable("companyId") Integer companyId) {
		return companyService.deleteCompany(companyId);
	}
}
