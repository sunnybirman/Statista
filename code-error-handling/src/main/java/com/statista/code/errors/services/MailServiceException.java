package com.statista.code.errors.services;

public class MailServiceException extends RuntimeException {

    public MailServiceException(String message) {
        super(message);
    }
}
