package com.big.friends.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final JavaMailSender mailSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private static final String FAIL_MSG = "Failed to send email";
    private static final String SUBJECT_MSG = "Confirm your email";
    private static final String MSG_FROM = "friends@gmail.com";

    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setText(email, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(SUBJECT_MSG);
            mimeMessageHelper.setFrom(MSG_FROM);
        } catch (MessagingException e){
            LOGGER.error(FAIL_MSG, e);
            throw new IllegalStateException(FAIL_MSG);
        }
    }
}
