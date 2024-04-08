package com.example.postandreplyservice.dao;

import com.example.postandreplyservice.entity.PostReply;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostReplyRepository extends MongoRepository<PostReply, String> {
    //List<PostReply> findAllByPostId(String postId);
}
