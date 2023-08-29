package com.statista.code.challenge.service.department;

import com.statista.code.challenge.model.DepartmentType;

public class TrainingAndEducation implements Department{

	@Override
	public String doBusiness() {
		return "Scheduling Trainings";
	}

	@Override
	public DepartmentType getDepartment() {
		return null;
	}

}
