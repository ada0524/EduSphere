package com.example.emailservice.service;
import com.example.emailservice.dao.RedisVerificationCodeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    private static final String SENDER = "forum5668@gmail.com";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisVerificationCodeManager redisVerificationCodeManager;

    public void sendVerificationCode(String email) {
        String code = generateVerificationCode(email);
        String subject = "Verification Code from Forum App";
        String content = "Hi,\n\n" +
                "You are receiving this message to verify your identity at Forum App. " +
                "Please confirm your identity using this code: " + code + "\n" +
                "This code will expire in 5 minutes.";
        sendSimpleMail(email, subject, content);
    }

    private String generateVerificationCode(String email) {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        String verificationCode = String.valueOf(code);
        redisVerificationCodeManager.saveVerificationCode(email, verificationCode);
        return verificationCode;
    }

    private void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SENDER);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Error happens:" + e);
        }
    }

    public boolean verifyVerificationCode(String email, String inputCode) {
        return redisVerificationCodeManager.verifyVerificationCode(email, inputCode);
    }
}
