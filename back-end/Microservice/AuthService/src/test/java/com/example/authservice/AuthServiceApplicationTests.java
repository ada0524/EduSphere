package com.example.authservice;

import com.example.authservice.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthServiceApplicationTests {

    @Autowired
    private AuthService userService;
    @Test
    void contextLoads() {
        System.out.println(userService.loadUserByUsername("admin@admin.com"));
    }

}
