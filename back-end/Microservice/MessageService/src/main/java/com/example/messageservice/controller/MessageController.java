package com.example.messageservice.controller;

import com.example.messageservice.dto.DataResponse;
import com.example.messageservice.dto.NewMessage;
import com.example.messageservice.secutiry.JwtPayload;
import com.example.messageservice.secutiry.JwtTokenProvider;
import com.example.messageservice.service.MessageService;
import com.example.messageservice.exceptions.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;


@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/messages")
    public DataResponse getAllMessages(@RequestHeader("Authorization") String token){
        JwtPayload payload = jwtTokenProvider.parseToken(token);
        String userType = payload.getUserType();
        Long userId = payload.getUserId();
        if (!userType.equals("ADMIN_USER")){
            throw new UnauthenticatedException("Admin Only");
        }


        return DataResponse.builder().success(true).data(this.messageService.getAllMessages()).build();
    }

    @PostMapping ("/users/contactus")
    public DataResponse postNewMessage(@Valid @RequestBody NewMessage newMessage, @RequestHeader("Authorization") String token){

        JwtPayload payload = jwtTokenProvider.parseToken(token);
        String userType = payload.getUserType();
        Integer userId = Math.toIntExact(payload.getUserId());

        this.messageService.postNewMessage(userId,newMessage);
        return DataResponse.builder().success(true).build();
    }

    @PatchMapping("/messages/{id}")
    public DataResponse changeMessageStatus(@PathVariable Integer id, @RequestParam String status, @RequestHeader("Authorization") String token){
        JwtPayload payload = jwtTokenProvider.parseToken(token);
        String userType = payload.getUserType();
        Long userId = payload.getUserId();
        if (!userType.equals("ADMIN_USER")){
            throw new UnauthenticatedException("Admin Only");
        }
        if (!(status.equals("Open") || status.equals("Close"))) {
            throw new ValidationException("Illegal Status");
        } else {
            this.messageService.changeMessageStatus(id, status);
            return DataResponse.builder().success(true).build();
        }
    }



}
