package com.example.history.service.remote;

import com.example.history.domain.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("post-and-reply-service")
public interface RemotePostService {
    @GetMapping("post-and-reply-service/posts/{postId}")
    Post getPostById(@RequestParam("postId") String postId, @RequestHeader("Authorization") String token);
}
