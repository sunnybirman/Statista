package com.statista.code.errors.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcmeMailService implements MailService {

    private static final Logger log = LoggerFactory.getLogger(AcmeMailService.class);

    @Override
    public void sendMail(String to, String content, Object attachment) throws MailServiceException {

        if(to.endsWith("foobar.org")) {
            log.error("Unsupported domain");
            throw new MailServiceException("Unsupported domain");
        }

    }
}
