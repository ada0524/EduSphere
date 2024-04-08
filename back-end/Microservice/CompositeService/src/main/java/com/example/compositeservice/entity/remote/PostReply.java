package com.example.compositeservice.entity.remote;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostReply {
    private String replyId;
    private Long userId;
    private String comment;
    private Boolean isActive;
    private Date dateCreated;
    private List<SubReply> subReplies;
}
