package com.example.messageservice.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class NewMessage {


    @Email
    private String email;

    @NotBlank
    private String subject;

    @NotBlank
    private String text;



}




