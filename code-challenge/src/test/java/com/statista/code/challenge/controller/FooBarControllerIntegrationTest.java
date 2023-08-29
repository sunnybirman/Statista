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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statista.code.challenge.model.Booking;
import com.statista.code.challenge.model.DepartmentType;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FooBarControllerIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private Booking mockBooking;

	@BeforeEach
	public void setup() {
		mockBooking  = new Booking("test", 22.2, "USD", 68312484, "test@em.com", DepartmentType.CUSTOMER_SUPPORT);
	}

	@Test
	public void createBooking_ValidRequest_Returns201() throws Exception {
		String requestContent = objectMapper.writeValueAsString(mockBooking);
		mockMvc.perform(post("/bookingservice/booking")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestContent))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$", not(empty())));
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
		.andExpect(jsonPath("$.message", is("Validation error")))
		.andExpect(jsonPath("$.errors", not(empty())));
	}

	@Test
	public void createBooking_InvalidParameterValue_Returns400() throws Exception {
		mockMvc.perform(post("/bookingservice/booking")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"invalidField\": \"invalidValue\"}"))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.status", is(400)))
		.andExpect(jsonPath("$.message", containsString("Validation error")))
		.andExpect(jsonPath("$.timestamp", is(notNullValue())));
	}

	@Test
	public void createBooking_NoSuchElementException_Returns400() throws Exception {
		// Perform a request that triggers a NoSuchElementException

		mockMvc.perform(get("/bookingservice/nonExistentResource"))
		.andExpect(status().isNotFound());

	}


	@Test public void updateBooking_ValidRequest_Returns200() throws Exception {
		
		String bookingID = createBookingResource();

		mockMvc.perform(put("/bookingservice/booking/{bookingId}", bookingID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mockBooking)))
		.andExpect(status().isOk()) .andExpect(jsonPath("$", is(notNullValue()))); 
	}


	@Test
	public void getBookingById_Returns200WithBooking() throws Exception {

		String bookingID = createBookingResource();

				mockMvc.perform(get("/bookingservice/booking/{bookingId}", bookingID))
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

	private String createBookingResource() throws Exception {
		// Create the booking resource
		String requestContent = objectMapper.writeValueAsString(mockBooking);
		MvcResult createResult = mockMvc.perform(post("/bookingservice/booking")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestContent))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$", not(empty())))
				.andReturn();

		// Extract the response content as a JSON string
		return createResult.getResponse().getContentAsString();
	}

}
