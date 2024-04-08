package com.example.postandreplyservice.controller;

import com.example.postandreplyservice.dto.PostReplyRequest;
import com.example.postandreplyservice.entity.PostReply;
import com.example.postandreplyservice.service.SubReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/{replyIdx}/subreplies")
public class SubReplyController {
    private final SubReplyService subReplyService;

    public SubReplyController(SubReplyService subReplyService) {
        this.subReplyService = subReplyService;
    }

    @PostMapping
    public ResponseEntity<PostReply> createSubReply(@PathVariable String postId, @PathVariable int replyIdx, @RequestBody PostReplyRequest reply, @RequestHeader("Authorization") String token) {
        subReplyService.createReply(postId, replyIdx, reply, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
