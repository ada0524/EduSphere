package com.example.compositeservice.entity.remote;

import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@Setter

public class Post {
    private String postId;
    private Long userId;
    private String title;
    private String content;
    private Boolean isArchived;
    private StatusEnum status;
    private Date dateCreated;
    private Date dateModified;
    private List<String> images;
    private List<String> attachments;
    private List<PostReply> postReplies;
}
