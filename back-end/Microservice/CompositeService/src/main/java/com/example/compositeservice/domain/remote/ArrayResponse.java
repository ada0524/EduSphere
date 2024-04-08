package com.example.compositeservice.domain.remote;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/* Status code with a list of key/URL/error message
       if success: {
        "200": file url array
       } else {
       "404": ["No such file"],
       "405": ["Method not allowed"],
       "500": ["Internal error"]
   }
*/
public class ArrayResponse {
    private boolean success;
    private int status;
    private List<String> data;
    private String message;
}
