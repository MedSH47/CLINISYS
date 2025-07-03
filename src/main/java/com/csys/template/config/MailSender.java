package com.csys.template.config;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component("MailSender")
public class MailSender {

    private final JavaMailSender javaMailSender;

    private static String from;
    private static String[] to;

    private final Logger log = LoggerFactory.getLogger(MailSender.class);

    public MailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${email.from}")
    public void setFrom(String fromMail) {
        from = fromMail;
    }

    @Value("${email.to}")
    public void setTo(String[] toMail) {
        to = toMail;
    }

   /**
    * Envoie un e-mail en texte brut.
    */
   public String sendMail(String to, String subject, String body) {
    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setFrom(from);
    mail.setTo(to);
    mail.setSubject(subject);
    mail.setText(body);

    log.info("Sending simple text mail...");
    javaMailSender.send(mail);
    log.info("Done!");
    return "Mail Sent Successfully";
}

   /**
    * Envoie un e-mail au format HTML.
    */
   public String sendHtmlMail(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // Le constructeur MimeMessageHelper permet de spécifier l'encodage, crucial pour les caractères français.
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        // Le 'true' ici est essentiel, il indique à MimeMessageHelper que le corps du message est du HTML.
        helper.setText(htmlBody, true);

        log.info("Sending HTML mail...");
        javaMailSender.send(mimeMessage);
        log.info("Done!");
        return "HTML Mail Sent Successfully";
   }


    public String sendMessageWithAttachment(String from, String to, String subject, String text, byte[] file) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        if (file != null) {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.addAttachment("attachment.pdf", new ByteArrayResource(file));
        } else {
            helper = new MimeMessageHelper(message, false, "UTF-8");
        }

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        javaMailSender.send(message);
        return "Mail with attachment sent successfully!";
    }
}