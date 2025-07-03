package com.csys.template.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogServiceMail {
    private final Logger log = LoggerFactory.getLogger(LogServiceMail.class);

    public void logNotification(String to, String subject, String body) {
        log.info("Notification sent to: {}, Subject: {}, Body: {}", to, subject, body);
    }
}
