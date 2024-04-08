package com.example.postandreplyservice.entity;

import java.util.Date;
import java.util.List;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostReply {
    @Id
    private String replyId;
    private Long userId;
    private String comment;
    private Boolean isActive;
    private Date dateCreated;
    private List<SubReply> subReplies;
}
