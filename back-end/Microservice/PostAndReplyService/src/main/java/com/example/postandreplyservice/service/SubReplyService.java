package com.example.postandreplyservice.service;

import com.example.postandreplyservice.dao.PostRepository;
import com.example.postandreplyservice.dto.PostReplyRequest;
import com.example.postandreplyservice.entity.Post;
import com.example.postandreplyservice.entity.PostReply;
import com.example.postandreplyservice.entity.StatusEnum;
import com.example.postandreplyservice.entity.SubReply;
import com.example.postandreplyservice.exception.NotFoundException;
import com.example.postandreplyservice.exception.PermissionDeniedException;
import com.example.postandreplyservice.secutiry.JwtPayload;
import com.example.postandreplyservice.secutiry.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubReplyService {
    private final PostRepository postRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public SubReplyService(PostRepository postRepository, JwtTokenProvider jwtTokenProvider) {
        this.postRepository = postRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /* Create sub reply */
    public Post createReply(String postId, int replyIdx, PostReplyRequest postReplyRequest, String token) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        List<PostReply> postReplies = post.getPostReplies();
        if (replyIdx < 0 || replyIdx >= postReplies.size()) {
            throw new NotFoundException("Post reply not found at index: " + replyIdx);
        }

        JwtPayload payload = jwtTokenProvider.parseToken(token);
        String userType = payload.getUserType();
        Long userId = payload.getUserId();

        if (!userType.equals("NORMAL_USER")) {
            throw new PermissionDeniedException("User type " + userType + " is not authorized to reply.");
        }

        StatusEnum status = post.getStatus();
        if (status != StatusEnum.PUBLISHED) {
            throw new PermissionDeniedException("You are not authorized to reply to a " + status.getStatusEnum() + " post");
        }

        SubReply reply = new SubReply();
        reply.setUserId(userId);
        reply.setComment(postReplyRequest.getComment());
        reply.setIsActive(true);
        reply.setDateCreated(new Date());

        PostReply targetReply = postReplies.get(replyIdx);
        List<SubReply> subReplies = targetReply.getSubReplies();
        subReplies.add(reply);

        postRepository.save(post);

        return post;
    }

    /* Delete reply */
    public void deleteReply(String postId, String replyId, String subReplyId, String token) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));
        // postReply and subPostReply entity may need change, one mongodb document only TODO may change mongodb to multiple documents?
        PostReply postReply = postRepository.findByPostIdAndPostRepliesReplyId(postId, replyId)
                .orElseThrow(() -> new NotFoundException("Post reply not found"));
        SubReply subReply =  postRepository.findPostByPostIdAndPostRepliesReplyIdAndPostRepliesSubRepliesSubReplyId(postId, replyId, subReplyId)
                .orElseThrow(() -> new NotFoundException("Post reply not found with id: " + replyId));

        JwtPayload payload = jwtTokenProvider.parseToken(token);
        String userType = payload.getUserType();
        Long userId = payload.getUserId();


        if (userType.equals("ADMIN_USER") || post.getUserId().equals(userId) || postReply.getUserId().equals(userId)) {
            // TODO change to real method
            postRepository.deleteById(subReply.getSubReplyId());
        }
        else {
            throw new PermissionDeniedException("You are not authorized to delete the reply");
        }
    }
}
