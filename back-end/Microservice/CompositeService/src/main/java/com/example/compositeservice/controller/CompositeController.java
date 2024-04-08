package com.example.compositeservice.controller;

import com.example.compositeservice.domain.CompositeRequest;
import com.example.compositeservice.domain.remote.ArrayResponse;
import com.example.compositeservice.domain.remote.AttachmentsResponse;
import com.example.compositeservice.domain.remote.PostRequest;
import com.example.compositeservice.entity.remote.Post;
import com.example.compositeservice.service.remote.FileServiceClient;
import com.example.compositeservice.service.remote.PostAndReplyServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/composite/posts")
public class CompositeController {

    private final PostAndReplyServiceClient postAndReplyServiceClient;
    private final FileServiceClient fileServiceClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public CompositeController(PostAndReplyServiceClient postAndReplyServiceClient, FileServiceClient fileServiceClient) {
        this.postAndReplyServiceClient = postAndReplyServiceClient;
        this.fileServiceClient = fileServiceClient;
    }

    @PostMapping("/create")
    //public ResponseEntity<Post> createPost(@RequestBody CompositeRequest compositeRequest, @RequestHeader("Authorization") String token) {
    public ResponseEntity<Post> createPost(@RequestParam("title") String title,
                @RequestParam("content") String content,
                @RequestParam("image") List<MultipartFile> images,
                @RequestParam("attachment") List<MultipartFile> attachments,
                @RequestHeader("Authorization") String token) {

        List<byte[]> imageBytes = convertMultipartListToByteArray(images);
        List<byte[]> attachmentBytes = convertMultipartListToByteArray(attachments);

        // Upload images and attachments to file service
        ArrayResponse imageUploadResponse = fileServiceClient.uploadPostImages(imageBytes);
        AttachmentsResponse attachmentUploadResponse = fileServiceClient.uploadPostAttachments(attachmentBytes);

        if (!imageUploadResponse.isSuccess() || !attachmentUploadResponse.isSuccess()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Create a new PostRequest with the URLs returned by file service
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle(title);
        postRequest.setContent(content);
        postRequest.setImages(imageUploadResponse.getData());
        postRequest.setAttachments(attachmentUploadResponse.getKeys());

        // Create the post using postAndReplyService
        Post createdPost = postAndReplyServiceClient.createPost(postRequest, "create", token);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }
//
//    @PostMapping("/create")
//    public ResponseEntity<Post> createPost(@RequestParam("title") String title,
//                                           @RequestParam("content") String content,
//                                           @RequestParam("image") List<MultipartFile> images,
//                                           @RequestParam("attachment") List<MultipartFile> attachments,
//                                           @RequestHeader("Authorization") String token) {
//
//        List<byte[]> imageBytes = convertMultipartListToByteArray(images);
//        List<byte[]> attachmentBytes = convertMultipartListToByteArray(attachments);
//
//        // Upload images and attachments to file service
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", token);
//
//        HttpEntity<List<byte[]>> imageRequest = new HttpEntity<>(imageBytes, headers);
//        ResponseEntity<ArrayResponse> imageUploadResponse = restTemplate.exchange(
//                "http://file-service/file-service/post/upload-images",
//                //"http://localhost:8081/file-service/post/upload-images",
//                HttpMethod.POST,
//                imageRequest,
//                ArrayResponse.class);
//
//        HttpEntity<List<byte[]>> attachmentRequest = new HttpEntity<>(attachmentBytes, headers);
//        ResponseEntity<AttachmentsResponse> attachmentUploadResponse = restTemplate.exchange(
//                "http://file-service/post/upload-attachments",
//                HttpMethod.POST,
//                attachmentRequest,
//                AttachmentsResponse.class);
//
//        if (imageUploadResponse.getStatusCode() != HttpStatus.OK || attachmentUploadResponse.getStatusCode() != HttpStatus.OK) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//
//        // Create a new PostRequest with the URLs returned by file service
//        PostRequest postRequest = new PostRequest();
//        postRequest.setTitle(title);
//        postRequest.setContent(content);
//        postRequest.setImages(imageUploadResponse.getBody().getData());
//        postRequest.setAttachments(attachmentUploadResponse.getBody().getKeys());
//
//        // Create the post using postAndReplyService
//        HttpHeaders postHeaders = new HttpHeaders();
//        postHeaders.set("Authorization", token);
//        HttpEntity<PostRequest> postRequestEntity = new HttpEntity<>(postRequest, postHeaders);
//        ResponseEntity<Post> createdPostResponse = restTemplate.exchange(
//                "http://post-and-reply-service/posts/create",
//                HttpMethod.POST,
//                postRequestEntity,
//                Post.class);
//
//        return ResponseEntity.status(createdPostResponse.getStatusCode()).body(createdPostResponse.getBody());
//    }


    private List<byte[]> convertMultipartListToByteArray(List<MultipartFile> multipartFiles) {
        List<byte[]> byteArrayList = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            try {
                byteArrayList.add(file.getBytes());
            } catch (IOException e) {
                // Handle conversion error
            }
        }
        return byteArrayList;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable String postId, @RequestHeader("Authorization") String token) {
        // Get the post from postAndReplyService
        Post post = postAndReplyServiceClient.getPostById(postId, token);

        return ResponseEntity.ok(post);
    }
}

