package com.example.postandreplyservice.service;

//import com.example.postandreplyservice.dao.PostElasticRepository;
import com.example.postandreplyservice.dao.PostElasticRepository;
import com.example.postandreplyservice.dao.PostRepository;
import com.example.postandreplyservice.dto.PostRequest;
import com.example.postandreplyservice.entity.Post;
//import com.example.postandreplyservice.entity.PostElastic;
import com.example.postandreplyservice.entity.PostElastic;
import com.example.postandreplyservice.entity.StatusEnum;
import com.example.postandreplyservice.exception.NotFoundException;
import com.example.postandreplyservice.exception.PermissionDeniedException;
import com.example.postandreplyservice.secutiry.JwtPayload;
import com.example.postandreplyservice.secutiry.JwtTokenProvider;
import com.example.postandreplyservice.service.remote.RemoteFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RemoteFileService remoteFileService;
    private final PostElasticRepository postElasticRepository;

    @Autowired
    public PostService(PostRepository postRepository, JwtTokenProvider jwtTokenProvider, RemoteFileService remoteFileService, PostElasticRepository postElasticRepository) {
        this.postRepository = postRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.remoteFileService = remoteFileService;
        this.postElasticRepository = postElasticRepository;
    }

    /* Get posts */
    // TODO the check of user type need to be changed based on the final implementation of user service
    public List<Post> findAllPosts(String token) {
        JwtPayload payload = jwtTokenProvider.parseToken(token);
        String userType = payload.getUserType();
        Long userId = payload.getUserId();

        List<Post> posts = postRepository.findAll();
        List<Post> filteredPosts = posts.stream()
                .filter(post -> post.getStatus() != StatusEnum.UNPUBLISHED || post.getUserId().equals(userId)) // only the owner of the draft can view the unpublished post
                .filter(post -> post.getStatus() != StatusEnum.HIDDEN || post.getUserId().equals(userId)) // only the owner of the post will be able to see and modify the hidden post
                .filter(post -> post.getStatus() != StatusEnum.BANNED || post.getUserId().equals(userId) || userType.equals("ADMIN_USER")) // banned post only be visible to the Admins and the owner of the post
                .filter(post -> post.getStatus() != StatusEnum.DELETED || post.getUserId().equals(userId)) // only post owner able to view deleted post
                .collect(Collectors.toList());

        return filteredPosts;
    }

    public List<PostElastic> searchPostsByKeyword(String keyword) {
        return postElasticRepository.findByTitleContainingOrContentContaining(keyword, keyword);
    }

    public Post findPostById(String postId, String token) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            throw new NotFoundException("Post not found");
        }

        JwtPayload payload = jwtTokenProvider.parseToken(token);
        String userType = payload.getUserType();
        Long userId = payload.getUserId();

        if (post.getStatus() == StatusEnum.UNPUBLISHED || post.getStatus() == StatusEnum.HIDDEN || post.getStatus() == StatusEnum.DELETED) {
            if (!post.getUserId().equals(userId)) {
                throw new PermissionDeniedException("You are not authorized to view a " + post.getStatus() + " post");
            }
        }
        if (post.getStatus() == StatusEnum.BANNED) {
            if (!userType.equals("admin") && !post.getUserId().equals(userId)) {
                throw new PermissionDeniedException("You are not authorized to view a " + post.getStatus() + " post");
            }
        }

        return post;
    }

    /* Create posts */
     public Post createPost(PostRequest postRequest, String action, String token) {
   // public Post createPost(String title,String content,List<MultipartFile> images,List<MultipartFile> attachments, String action, String token) {
        JwtPayload payload = jwtTokenProvider.parseToken(token);
        Long userId = payload.getUserId();
        String userType = payload.getUserType();

        if (userType.equals("UNVERIFIED_USER")) {
            throw new PermissionDeniedException("Please confirm your email first before creating a post.");
        }
        if (userType.equals("ADMIN_USER")) {
            throw new PermissionDeniedException("Admins are not allowed to create posts.");
        }

        // Generate new post
        Post post = new Post();
        post.setUserId(userId);
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
//        post.setTitle(title);
//        post.setContent(content);
        post.setIsArchived(false);
        if (action.equals("DRAFT")) post.setStatus(StatusEnum.UNPUBLISHED);
        else post.setStatus(StatusEnum.PUBLISHED);
        post.setDateCreated(new Date());
        post.setDateModified(new Date());

        if (postRequest.getImages() != null) {
            System.out.println("image is not null");
            // ArrayResponse response = remoteFileService.uploadPostImages(images);
            post.setImages(postRequest.getImages());
        } else {
            System.out.println("image is null");
            post.setImages(new ArrayList<>());
        }
        if (postRequest.getAttachments() != null) {
            System.out.println("attachment is not null");
            // AttachmentsResponse response = remoteFileService.uploadPostAttachments(attachments);
            post.setAttachments(postRequest.getAttachments());
        } else {
            System.out.println("attachment is null");
            post.setAttachments(new ArrayList<>());
        }
        post.setPostReplies(new ArrayList<>());
        return postRepository.save(post);
    }

    public Post updatePostContent(String postId, PostRequest postRequest, String token) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        JwtPayload payload = jwtTokenProvider.parseToken(token);
        Long userId = payload.getUserId();
        if (post.getUserId() != userId) {
            throw new PermissionDeniedException("You are not authorized to update posts that were not created by you.");
        }
        if (post.getStatus().equals(StatusEnum.BANNED) || post.getStatus().equals(StatusEnum.DELETED)) {
            throw new PermissionDeniedException("You are not authorized to update post that has been BANNED or DELETED.");
        }

        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setDateModified(new Date());
        if (postRequest.getImages() != null) {
            // ArrayResponse response = remoteFileService.uploadPostImages(postRequest.getImages());
            post.setImages(postRequest.getImages() );
        }
        if (postRequest.getAttachments() != null) {
            // AttachmentsResponse response = remoteFileService.uploadPostAttachments(postRequest.getAttachments());
            post.setAttachments(postRequest.getAttachments());
        }
        return postRepository.save(post);
    }

    public Post updatePostIsArchived(String postId, Boolean isArchived, String token) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        JwtPayload payload = jwtTokenProvider.parseToken(token);
        Long userId = payload.getUserId();
        if (post.getUserId() != userId) {
            throw new PermissionDeniedException("You are not authorized to archive posts that were not created by you.");
        }
        post.setIsArchived(isArchived);
        return postRepository.save(post);
    }

    public Post updatePostStatus(String postId, String statusName, String token) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        JwtPayload payload = jwtTokenProvider.parseToken(token);
        Long userId = payload.getUserId();
        if (post.getStatus().equals(StatusEnum.BANNED)) {
            throw new PermissionDeniedException("You are not authorized to update post status that has been BANNED.");
        }
        if (post.getUserId() != userId) {
            throw new PermissionDeniedException("You are not authorized to update post status that were not created by you.");
        }
        post.setStatus(StatusEnum.valueOf(statusName.toUpperCase()));
        return postRepository.save(post);
    }


    /* Delete posts */
    public Post deletePost(String postId, String token) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));
        JwtPayload payload = jwtTokenProvider.parseToken(token);
        Long userId = payload.getUserId();
        if (post.getUserId() != userId) {
            throw new PermissionDeniedException("You are not authorized to delete post status that were not created by you.");
        }
        post.setStatus(StatusEnum.DELETED);
        return postRepository.save(post);
    }
}
