package com.example.messageservice.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataResponse {
    private Boolean success;
    private String message;
    private Object data;

}
