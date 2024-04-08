package com.example.emailservice.controller;

import com.example.emailservice.entity.User;
import com.example.emailservice.service.EmailService;
import com.example.emailservice.service.remote.RemoteUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RemoteUserService remoteUserService;

    @PostMapping("/verificationRequest")
    public String verificationRequest(@RequestBody User user) {
        rabbitTemplate.convertAndSend("email-exchange", "code", user.getEmail());
        return "Verification code sent to your email successfully";
    }

    @PostMapping("/verify")
    public String verify(@RequestBody User user, @RequestParam String inputCode) {
        // check code is correct or not
        if (!emailService.verifyVerificationCode(user.getEmail(), inputCode)) {
            return "Verification code incorrect or expired";
        }else{
            // if correct, modify user's status to verified
            remoteUserService.emailVerified(user.getEmail());
            return "Verified successfully";
        }
    }
}
