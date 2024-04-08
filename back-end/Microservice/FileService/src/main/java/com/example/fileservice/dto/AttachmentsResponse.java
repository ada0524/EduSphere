package com.example.fileservice.dto;
import com.example.fileservice.entity.Attachment;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/* Status code with a list of key/URL/error message and a list of file types
       if success: {
        "200": file url list, file type list
       } else {
       "404": ["No such file"],[]
       "405": ["Method not allowed"],[]
       "500": ["Internal error"],[]
   }
*/
public class AttachmentsResponse {
    private boolean success;
    private int status;
    private List<Attachment> files;
    private List<String> keys;
    private String message;
}
