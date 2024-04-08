package com.example.compositeservice.entity.remote;

import lombok.*;

import java.util.Date;

@Getter
@Setter
public class SubReply {

    private String subReplyId;
    private Long userId;
    private String comment;
    private Boolean isActive;
    private Date dateCreated;

}
