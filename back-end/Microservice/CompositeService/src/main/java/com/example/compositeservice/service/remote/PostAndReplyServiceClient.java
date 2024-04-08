package com.example.compositeservice.service.remote;

import com.example.compositeservice.domain.remote.PostRequest;
import com.example.compositeservice.entity.remote.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient("post-and-reply-service")
public interface PostAndReplyServiceClient {

    @PostMapping("post-and-reply-service/posts/create")
    Post createPost(@RequestBody PostRequest postRequest, @RequestParam("action") String action, @RequestHeader("Authorization") String token);

    @GetMapping("post-and-reply-service/posts/{postId}")
    Post getPostById(@PathVariable String postId, @RequestHeader("Authorization") String token);
}
