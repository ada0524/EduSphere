package com.example.postandreplyservice.entity;

import java.util.Date;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubReply {
    @Id
    private String subReplyId;
    private Long userId;
    private String comment;
    private Boolean isActive;
    private Date dateCreated;

}
