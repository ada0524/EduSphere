package com.example.postandreplyservice.service;

import com.example.postandreplyservice.dao.PostReplyRepository;
import com.example.postandreplyservice.dao.PostRepository;
import com.example.postandreplyservice.dto.PostReplyRequest;
import com.example.postandreplyservice.entity.Post;
import com.example.postandreplyservice.entity.PostReply;
import com.example.postandreplyservice.entity.StatusEnum;
import com.example.postandreplyservice.exception.NotFoundException;
import com.example.postandreplyservice.exception.PermissionDeniedException;
import com.example.postandreplyservice.secutiry.JwtPayload;
import com.example.postandreplyservice.secutiry.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostReplyService {

    private final PostReplyRepository postReplyRepository;
    private final PostRepository postRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public PostReplyService(PostReplyRepository postReplyRepository, PostRepository postRepository, JwtTokenProvider jwtTokenProvider) {
        this.postReplyRepository = postReplyRepository;
        this.postRepository = postRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /* Get reply (no need anymore) */
//    public List<PostReply> getAllRepliesByPostId(String postId) {
//        return postReplyRepository.findAllByPostId(postId);
//    }

    /* Create reply */
    public Post createReply(String postId, PostReplyRequest postReplyRequest, String token) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));
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

        PostReply reply = new PostReply();
        reply.setUserId(userId);
        reply.setComment(postReplyRequest.getComment());
        reply.setIsActive(true);
        reply.setDateCreated(new Date());
        reply.setSubReplies(new ArrayList<>());

        post.getPostReplies().add(reply);
        return postRepository.save(post);
    }

//    /* Update reply */
//    public Optional<PostReply> updateReply(String postId, String replyId, PostReplyRequest postReplyRequest, String token) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new NotFoundException("Post not found"));
//        JwtPayload payload = jwtTokenProvider.parseToken(token);
//        String userType = payload.getUserType();
//        Long userId = payload.getUserId();
//        Optional<PostReply> existingReply = postReplyRepository.findById(replyId);
//        if (existingReply.isPresent()) {
//            PostReply reply = existingReply.get();
//            if (reply.getPostId().equals(postId)) {
//                updatedReply.setReplyId(replyId);
//                updatedReply.setPostId(postId);
//                return Optional.of(postReplyRepository.save(updatedReply));
//            }
//        }
//        return Optional.empty();
//    }

    /* Delete reply */
//    public void deleteReply(String postId, String replyId, String token) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new NotFoundException("Post not found"));
//        JwtPayload payload = jwtTokenProvider.parseToken(token);
//        String userType = payload.getUserType();
//        Long userId = payload.getUserId();
//
//        PostReply postReply =  postReplyRepository.findById(replyId)
//                .orElseThrow(() -> new NotFoundException("Post reply not found with id: " + replyId));
//        if (userType.equals("ADMIN_USER") || post.getUserId().equals(userId) || postReply.getUserId().equals(userId)) {
//            postReplyRepository.deleteById(replyId);
//        }
//        else {
//            throw new PermissionDeniedException("You are not authorized to delete the reply");
//        }
//    }

    public void deleteReply(String postId, int replyIdx, String token) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));
        JwtPayload payload = jwtTokenProvider.parseToken(token);
        String userType = payload.getUserType();
        Long userId = payload.getUserId();

        // Get post replies
        List<PostReply> replies = post.getPostReplies();

        // Check if index is valid
        if (replyIdx < 0 || replyIdx >= replies.size()) {
            throw new NotFoundException("Post reply not found with index: " + replyIdx);
        }

        PostReply postReply = replies.get(replyIdx);

        if (userType.equals("ADMIN_USER") || post.getUserId().equals(userId) || postReply.getUserId().equals(userId)) {
            // Delete reply by index
            replies.remove(replyIdx);
            postRepository.save(post); // Save the changes to the post
        } else {
            throw new PermissionDeniedException("You are not authorized to delete the reply");
        }
    }
}
