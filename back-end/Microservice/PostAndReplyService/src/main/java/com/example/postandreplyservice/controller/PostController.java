package com.example.postandreplyservice.controller;

import com.example.postandreplyservice.dto.PostRequest;
import com.example.postandreplyservice.entity.Post;
import com.example.postandreplyservice.entity.PostElastic;
import com.example.postandreplyservice.service.PostService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController (PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/all")
    public List<Post> findAllPosts(@RequestHeader("Authorization") String token) {
        return postService.findAllPosts(token);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostElastic>> searchPosts(@RequestParam String keyword) {
        List<PostElastic> posts = postService.searchPostsByKeyword(keyword);


        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable String postId, @RequestHeader("Authorization") String token) {
        Post post = postService.findPostById(postId, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    /* Create post */
    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest, @RequestParam("action") String action, @RequestHeader("Authorization") String token) {
        Post createdPost = postService.createPost(postRequest, action, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

//    @PostMapping("/create")
//    public ResponseEntity<Post> createPost(@RequestParam("title") String title,
//                                           @RequestParam("content") String content,
//                                           @RequestParam("images") List<MultipartFile> images,
//                                           @RequestParam("attachments") List<MultipartFile> attachments,
//                                           @RequestParam("action") String action, @RequestHeader("Authorization") String token) {
//        Post createdPost = postService.createPost(title, content, images, attachments, action, token);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
//    }
    /* Update post */
    @PatchMapping("/{postId}")
    public ResponseEntity<Post> updatePostContent(@PathVariable String postId, @RequestBody PostRequest postRequest, @RequestHeader("Authorization") String token) {
        Post updatedPost = postService.updatePostContent(postId, postRequest, token);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
    }

    @PutMapping("/{postId}/archive")
    public ResponseEntity<?> updatePostIsArchived(@PathVariable String postId, @RequestParam Boolean isArchived, @RequestHeader("Authorization") String token) {
        postService.updatePostIsArchived(postId, isArchived, token);
        String message = "Post with ID " + postId + " successfully archived.";
        return ResponseEntity.ok().body(message);
    }

    @PutMapping("/{postId}/status")
    public ResponseEntity<?> updatePostStatus(@PathVariable String postId, @RequestParam String status, @RequestHeader("Authorization") String token) {
        postService.updatePostStatus(postId, status, token);
        String message = "Post with ID " + postId + " successfully updated status to " + status + ".";
        return ResponseEntity.ok().body(message);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable String postId, @RequestHeader("Authorization") String token) {
        postService.deletePost(postId, token);
        String message = "Post with ID " + postId + " successfully deleted.";
        return ResponseEntity.ok().body(message);
    }
}
