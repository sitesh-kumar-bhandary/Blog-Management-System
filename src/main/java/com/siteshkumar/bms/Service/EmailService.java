package com.siteshkumar.bms.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailService {
    
    private final JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String body){
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            javaMailSender.send(mail);
            log.info("Comment notification email sent to {}", to);
        }

        catch(Exception ex){
            log.error("Error occurred while sending email: ", ex);
        }
    }
}
