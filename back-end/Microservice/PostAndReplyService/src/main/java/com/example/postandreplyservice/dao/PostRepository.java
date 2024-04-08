package com.example.postandreplyservice.dao;

import com.example.postandreplyservice.entity.Post;
import com.example.postandreplyservice.entity.PostReply;
import com.example.postandreplyservice.entity.SubReply;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    Optional<PostReply> findByPostIdAndPostRepliesReplyId(String postId, String replyId);

    Optional<SubReply> findPostByPostIdAndPostRepliesReplyIdAndPostRepliesSubRepliesSubReplyId(String postId, String replyId, String subReplyId);
}

