package com.example.emailservice.service.remote;

import com.example.emailservice.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-service")
public interface RemoteUserService {

    @PostMapping("user-service/add")
    ResponseEntity<User> addUser(@RequestBody User user);

    @PostMapping("user-service/emailVerified")
    void emailVerified(@RequestParam String email);
}
