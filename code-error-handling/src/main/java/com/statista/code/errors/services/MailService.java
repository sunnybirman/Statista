package com.statista.code.errors.services;

public interface MailService {
    void sendMail(String to, String content, Object attachment) throws MailServiceException;
}
