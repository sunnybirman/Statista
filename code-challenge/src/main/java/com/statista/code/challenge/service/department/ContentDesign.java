package com.statista.code.challenge.service.department;

import com.statista.code.challenge.model.DepartmentType;

public class ContentDesign implements Department{

	@Override
	public String doBusiness() {
		
		return "Creating immersive content";
	}

	@Override
	public DepartmentType getDepartment() {
		return null;
	}

}
