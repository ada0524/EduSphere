package com.example.userservice;

//import com.beaconfire.userservice.service.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

//    @Mock
//    private JavaMailSender mailSender;
//
//    @InjectMocks
//    private EmailService emailService;
//
//    @Test
//    public void testSendSimpleMail() {
//        String to = "amberdu722@gmail.com";
//        String subject = "Verification Code from Forum App";
//        String content = "Verification Code";
//
//        // send email
//        emailService.sendSimpleMail(to, subject, content);
//
//        // verify that the email was sent
//        verify(mailSender).send(any(SimpleMailMessage.class));
//    }
}
