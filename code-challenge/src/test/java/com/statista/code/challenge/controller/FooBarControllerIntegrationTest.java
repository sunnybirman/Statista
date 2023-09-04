package com.statista.code.challenge.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statista.code.challenge.model.Booking;
import com.statista.code.challenge.model.DepartmentType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FooBarControllerIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private Booking mockBooking;

	@Before
	public void setup() {
		mockBooking = new Booking("test", 22.2, "USD", 68312484, "test@em.com", DepartmentType.CUSTOMER_SUPPORT);
	}

	@Test
	public void createBooking_ValidRequest_Returns201() throws Exception {
		String requestContent = objectMapper.writeValueAsString(mockBooking);
		mockMvc.perform(post("/bookingservice/booking")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestContent))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$", is(not(empty()))));
	}

	@Test
	public void createBooking_InvalidRequest_Returns400() throws Exception {
		/* Setting invalid booking properties */
		Booking invalidBooking = new Booking();

		mockMvc.perform(post("/bookingservice/booking")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidBooking)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.status", is(400)))
		.andExpect(jsonPath("$.message", is("Validation Error")))
		.andExpect(jsonPath("$.field_errors", is(not(empty()))));
	}

	@Test
	public void createBooking_InvalidParameterValue_Returns400() throws Exception {
		mockMvc.perform(post("/bookingservice/booking")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"invalidField\": \"invalidValue\"}"))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.status", is(400)))
		.andExpect(jsonPath("$.message", containsString("Validation Error")))
		.andExpect(jsonPath("$.timestamp", is(notNullValue())));
	}

	@Test
	public void createBooking_NoSuchElementException_Returns400() throws Exception {
		// Perform a request that triggers a NoSuchElementException
		mockMvc.perform(get("/bookingservice/nonExistentResource"))
		.andExpect(status().isNotFound());
	}

	@Test
	public void updateBooking_ValidRequest_Returns200() throws Exception {
		mockMvc.perform(put("/bookingservice/booking/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mockBooking)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", is(notNullValue())));
	}

	@Test
	public void getBookingById_Returns200WithBooking() throws Exception {
		createBookingResource();

		mockMvc.perform(get("/bookingservice/booking/{bookingID}", 2))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", is(notNullValue())));
	}

	@Test
	public void getCurrencies_Returns200WithCurrencies() throws Exception {
		mockMvc.perform(get("/bookingservice/bookings/currencies"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", containsInAnyOrder("USD")));
	}

	@Test
	public void getSumOfCurrency_Returns200WithSum() throws Exception {
		mockMvc.perform(get("/bookingservice/sum/USD"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", is(notNullValue())));
	}

	@Test
	public void getBookingByDepartment_ValidRequest_Returns200() throws Exception {
		
		createBookingResource();
		// Prepare a valid department for testing
		DepartmentType department = DepartmentType.CUSTOMER_SUPPORT; // Replace with a valid department name

		// Perform the GET request and perform assertions
		mockMvc.perform(get("/bookingservice/bookings/department/{department}", department)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", not(empty())));
	}

	@Test
	public void getBookingByDepartment_InvalidDepartment_Returns404() throws Exception {
		// Prepare an invalid department for testing
		String invalidDepartment = "NonExistentDepartment"; 

		// Perform the GET request and perform assertions
		mockMvc.perform(get("/bookingservice/bookings/department/{department}", invalidDepartment)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}

	private String createBookingResource() throws Exception {
		// Create the booking resource
		String requestContent = objectMapper.writeValueAsString(mockBooking);
		MvcResult createResult = mockMvc.perform(post("/bookingservice/booking")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestContent))
				.andReturn();

		// Extract the response content as a JSON string
		return createResult.getResponse().getContentAsString();
	}


}
