package com.statista.code.challenge.service.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailNotification implements Notification{

	private static final Logger logger = LoggerFactory.getLogger(EmailNotification.class);
	
	@Override
	public void send() {
		logger.info("Email notification sent");
	}

}
