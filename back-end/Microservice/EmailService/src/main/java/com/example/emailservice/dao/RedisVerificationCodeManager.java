package com.example.emailservice.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class RedisVerificationCodeManager {
    private static final long VERIFICATION_CODE_DURATION = 300000; // Verification code validity duration: 5 minutes

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void saveVerificationCode(String email, String code) {
        redisTemplate.opsForValue().set(email, code, VERIFICATION_CODE_DURATION, TimeUnit.MILLISECONDS);
    }

    public boolean verifyVerificationCode(String email, String inputCode) {
        String storedCode = redisTemplate.opsForValue().get(email);
        if (storedCode != null && storedCode.equals(inputCode)) {
            redisTemplate.delete(email);
            return true;
        }
        return false;
    }
}