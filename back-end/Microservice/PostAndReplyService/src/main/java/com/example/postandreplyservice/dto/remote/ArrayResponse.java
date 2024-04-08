package com.example.postandreplyservice.dto.remote;


import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ArrayResponse {
    private boolean success;
    private int status;
    private List<String> data;
    private String message;
}