package com.example.emailservice;

import com.example.emailservice.service.EmailService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSenderMock;

    @InjectMocks
    private EmailService emailService;

    @Before
    public void setUp() {
        emailService = new EmailService();
        emailService.mailSender = mailSenderMock;
    }

    @Test
    public void testSendSimpleMail() {
        String to = "amberdu722@gmail.com";
        String subject = "Verification Code from Forum App";
        String content = "Verification Code";

        // send email
        emailService.sendSimpleMail(to, subject, content);

        // verify that the email was sent
        verify(mailSenderMock).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testGenerateVerificationCode_NewEmail() {
        String email = "amberdu722@gmail.com";
        String code = emailService.generateVerificationCode(email);

        Map<String, String> verificationCodes = emailService.verificationCodes;
        assertTrue(verificationCodes.containsKey(email));
        assertEquals(code, verificationCodes.get(email));
    }

    @Test
    public void testGenerateVerificationCode_ExistingEmail() {
        String email = "amberdu722@gmail.com";
        String code = emailService.generateVerificationCode(email);
        emailService.verificationCodes.put(email, code);
        String newCode = emailService.generateVerificationCode(email);
        assertEquals(newCode, code);
    }

    @Test
    public void testVerifyVerificationCode_ValidCode() {
        String email = "amberdu722@gmail.com";
        String code = "123456";
        emailService.verificationCodes.put(email, code);
        emailService.lastVerificationCodeTime = System.currentTimeMillis();

        assertTrue(emailService.verifyVerificationCode(email, code));
    }

    @Test
    public void testVerifyVerificationCode_ExpiredCode() {
        String email = "amberdu722@gmail.com";
        String code = "123456";
        emailService.verificationCodes.put(email, code);
        emailService.lastVerificationCodeTime = System.currentTimeMillis() - 2 * EmailService.VERIFICATION_CODE_DURATION;

        assertFalse(emailService.verifyVerificationCode(email, code));
    }
}
