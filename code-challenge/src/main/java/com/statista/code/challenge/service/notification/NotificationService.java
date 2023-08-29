package com.statista.code.challenge.service.notification;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	
	
	public void sendNotification(Notification notification) {
		notification.send();
	}

}
