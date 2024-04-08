package com.example.postandreplyservice.controller;

import com.example.postandreplyservice.dto.PostReplyRequest;
import com.example.postandreplyservice.entity.PostReply;
import com.example.postandreplyservice.service.PostReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/replies")
public class PostReplyController {

    private final PostReplyService postReplyService;

    public PostReplyController(PostReplyService postReplyService) {
        this.postReplyService = postReplyService;
    }

//    @GetMapping
//    public List<PostReply> getAllReplies(@PathVariable String postId) {
//        return null;
//        // return postReplyService.getAllRepliesByPostId(postId);
//    }

    @PostMapping
    public ResponseEntity<PostReply> createReply(@PathVariable String postId, @RequestBody PostReplyRequest reply, @RequestHeader("Authorization") String token) {
        postReplyService.createReply(postId, reply, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping("/{replyIdx}")
    public ResponseEntity<PostReply> updateReply(@PathVariable String postId, @PathVariable String replyId, @RequestBody PostReply reply) {

        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{replyIdx}")
    public ResponseEntity<Void> deleteReply(@PathVariable String postId, @PathVariable("replyIdx") int replyIdx, @RequestHeader("Authorization") String token) {
        postReplyService.deleteReply(postId, replyIdx, token);
        return ResponseEntity.noContent().build();
    }
}
