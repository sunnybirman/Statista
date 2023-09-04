package com.statista.code.challenge.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

@Configuration
public class MessageConfig {
	
	@Autowired
	private MessageSource messageSource;
	
	@Value("${error.response.contact}")
    private String contact;
	

	public String getContact() {
		return contact;
	}

	public String getBookingConfirmationMessage() {
		return messageSource.getMessage("booking.confirmation.message", null, LocaleContextHolder.getLocale());
	}

	public String getBookingUpdateMessage() {
		return messageSource.getMessage("booking.update.message", null, LocaleContextHolder.getLocale());
	}

	public String getInValidDepartmentErrorMessage() {
		return messageSource.getMessage("validation.department.error", null, LocaleContextHolder.getLocale());
	}

	public String getValidationErrorMessage() {
		return messageSource.getMessage("validation.error.message", null, LocaleContextHolder.getLocale());
	}

	public String getEmailNotificationSucess() {
		return messageSource.getMessage("notification.email.success", null, LocaleContextHolder.getLocale());
	}

	public String getEmailNotificationFailure() {
		return messageSource.getMessage("notification.email.failure", null, LocaleContextHolder.getLocale());
	}
	
	public String getGenericErrorMessage() {
		return messageSource.getMessage("error.generic.message", null, LocaleContextHolder.getLocale());
	}
}
