package com.example.authservice.service.remote;

import com.example.authservice.domain.response.AllUsersResponse;
import com.example.authservice.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-service")
public interface RemoteUserService {

    @GetMapping("user-service/getAll")
    AllUsersResponse getAllUsers();

    @GetMapping("user-service/getUserByEmail")
    ResponseEntity<User> getUserByEmail(@RequestParam("email") String email);

    @PostMapping("user-service/add")
    ResponseEntity<User> addUser(@RequestBody User user);
}
