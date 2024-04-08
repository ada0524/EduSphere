package com.example.fileservice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/* Status code with key/URL/error message
       if success: {
        "200": file url
       } else {
       "404": "No such file",
       "405": "Method not allowed",
       "500": "Internal error"
   }
*/
public class DataResponse {
    private boolean success;
    private int status;
    private String message;
}
