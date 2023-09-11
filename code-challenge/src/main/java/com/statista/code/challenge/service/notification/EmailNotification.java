package com.statista.code.challenge.service.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailNotification implements NotificationService{

	private static final Logger logger = LoggerFactory.getLogger(EmailNotification.class);
	

	
	@Override
	public boolean sendNotification(String recipient, String message) {
		// Delegate Email sending Job to decoupled Kafka application
		logger.info("Email notification sent to {} with message {}", recipient, message);
		
		// If Kafka application is down or some other issue return false, otherwise true
		return true;
		
	}

}
