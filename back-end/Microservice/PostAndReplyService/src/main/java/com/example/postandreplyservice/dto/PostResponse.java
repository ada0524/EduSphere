package com.example.postandreplyservice.dto;

import com.example.postandreplyservice.entity.PostReply;

import java.util.Date;
import java.util.List;

public class PostResponse {
    private String postId;
    private Long userId;
    private String title;
    private String content;
    private Boolean isArchived;
    private String status;
    private Date dateCreated;
    private Date dateModified;
    private List<String> images;
    private List<String> attachments;
    private List<PostReply> postReplies;
}
