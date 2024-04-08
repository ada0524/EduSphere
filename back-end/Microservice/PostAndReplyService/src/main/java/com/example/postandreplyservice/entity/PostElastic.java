package com.example.postandreplyservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "posts")
public class PostElastic {
    @Id
    private String postId;

    private Long userId;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String content;

    private Boolean isArchived;

    private StatusEnum status;

//    @Field(type = FieldType.Date)
    private Date dateCreated;

    private Date dateModified;

    private List<String> images;

    private List<String> attachments;

    private List<PostReply> postReplies;
}
