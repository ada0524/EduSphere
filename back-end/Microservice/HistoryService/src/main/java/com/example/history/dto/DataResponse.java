package com.example.history.dto;

import lombok.*;

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
