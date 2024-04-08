package com.example.emailservice.util;

import com.example.emailservice.service.EmailService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQReceive {
    @Autowired
    private EmailService emailService;

    @RabbitListener(bindings =
    @QueueBinding(
            value = @Queue(value = "email-queue"),
            exchange = @Exchange(name = "email-exchange", type = "direct"),
            key = {"code"}
    )
    )

    private void sendEMail(String email) {
        emailService.sendVerificationCode(email);
        System.out.println("Email sent successfully.");
    }
}