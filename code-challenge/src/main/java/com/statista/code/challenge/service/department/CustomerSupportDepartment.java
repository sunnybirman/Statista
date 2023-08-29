package com.statista.code.challenge.service.department;

import org.springframework.stereotype.Service;

import com.statista.code.challenge.model.DepartmentType;

@Service
public class CustomerSupportDepartment implements Department {

	@Override
	public String doBusiness() {
		// Raise ticket for the user concern
		
		return "Ticket raised";

	}

	@Override
	public DepartmentType getDepartment() {
		return DepartmentType.CUSTOMER_SUPPORT;
	}

}
