/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csys.template.config;

import java.util.logging.Level;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 *
 * @author admin
 */
@Component("MailSender")
public class MailSender {

    @Autowired
    JavaMailSender javaMailSender;

    private static String from;

    private static String[] to;

    @Value("${email.from}")
    public void setFrom(String fromMail) {
        from = fromMail;
    }

    @Value("${email.to}")
    public void setTo(String[] toMail) {
        to = toMail;
    }

    private final Logger log = LoggerFactory.getLogger(MailSender.class);

    public String sendMail(String subject, String body) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(from);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);

        log.info("Sending...");

        javaMailSender.send(mail);

        log.info("Done!");
        return "Mail Sended Successfully";
    }

    public String sendMail(String[] to, String subject, String body) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("nihel.turki@csys.com.tn");
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);

        log.info("Sending...");

        javaMailSender.send(mail);

        log.info("Done!");
        return "Mail Sended Successfully";
    }

    public String sendMail(String from, String to, String subject, String body) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(from);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);

        log.info("Sending...");

        javaMailSender.send(mail);

        log.info("Done!");
        return "Mail Sended Successfully";
    }

    public String sendMessageWithAttachment(String from, String to, String subject, String text, byte[] file) throws MessagingException {
        try {
            // ...

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper;
            if (file != null) {
                helper = new MimeMessageHelper(message, true);
                helper.addAttachment("demandePEC.pdf", new ByteArrayResource(file));
            } else {
                helper = new MimeMessageHelper(message, false);
            }
            log.debug("from  : {}", from);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            log.info("Sending...");
            javaMailSender.send(message);
            log.info("Done!");
            return "Mail Sended Successfully";
        } catch (SendFailedException ex) {
            java.util.logging.Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
    }

}
