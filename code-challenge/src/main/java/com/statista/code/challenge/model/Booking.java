package com.statista.code.challenge.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Booking {

	@NotBlank (message = "Please provide description")
    private String description;
	
	@DecimalMin(value = "0.01") 
    private double price;
	
	@NotBlank(message = "Please provide currency")
    private String currency;
	
	@Min(0)
	@JsonProperty("subscription_start_date")
    private long subscriptionStartDate;
    
	@Email(message = "Please provide correct email")
    private String email;
    
    private DepartmentType departmentType;
	
	public Booking() {
		super();
	}
    
    
	public Booking(String description, double price, String currency, long subscriptionStartDate,
			String email, DepartmentType departmentType) {
		super();
		this.description = description;
		this.price = price;
		this.currency = currency;
		this.subscriptionStartDate = subscriptionStartDate;
		this.email = email;
		this.departmentType = departmentType;
	}



	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public long getSubscriptionStartDate() {
		return subscriptionStartDate;
	}


	public void setSubscriptionStartDate(long subscriptionStartDate) {
		this.subscriptionStartDate = subscriptionStartDate;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public DepartmentType getDepartment() {
		return departmentType;
	}


	public void setDepartment(DepartmentType departmentType) {
		this.departmentType = departmentType;
	}
    
    
}
