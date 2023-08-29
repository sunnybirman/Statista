package com.statista.code.challenge.service.department;

import com.statista.code.challenge.model.DepartmentType;

public class MarketInsights implements Department{

	@Override
	public String doBusiness() {
		return "Generating Market Insights";
	}

	@Override
	public DepartmentType getDepartment() {
		return null;
	}

}
