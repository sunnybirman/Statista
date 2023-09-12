package com.statista.code.challenge.model;

public enum DepartmentType {
	MARKET_INSIGHTS("MarketInsights"),
	CONTENT_DESIGN("ContentDesign"),
	CUSTOMER_SUPPORT("CustomerSupport"),
	TRAINING_AND_EDUCATION("Training&Education");

	private final String departmentName;

	DepartmentType(String departmentName) {
		this.departmentName = departmentName;
	}

	public static DepartmentType fromString(String text) {
		for (DepartmentType departmentType : DepartmentType.values()) {
			if (departmentType.departmentName.equalsIgnoreCase(text)) {
				return departmentType;
			}
		}
		throw new IllegalArgumentException("No department with name " + text + " found");
	}
}
