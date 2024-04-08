package com.example.fileservice.controller;

import com.example.fileservice.dto.ArrayResponse;
import com.example.fileservice.dto.AttachmentsResponse;
import com.example.fileservice.dto.DataResponse;
import com.example.fileservice.entity.Attachment;
import com.example.fileservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
public class FileController {

    private FileService fileService;

    @Autowired
    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    // update user's profile avatar
    @PostMapping("/user/upload-profile")
    public DataResponse uploadUserProfile(@RequestParam("image") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            String key = this.fileService.uploadUserProfile(bytes);
            return DataResponse.builder()
                    .success(true)
                    .status(200)
                    .message(key)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return DataResponse.builder()
                    .success(false)
                    .status(500)
                    .message("Internal error")
                    .build();
        }
    }

    @GetMapping("/user/profile")
    public DataResponse getUserProfile(@RequestParam("key") String imageKey) {

        try{
            String url = this.fileService.getProfileImage(imageKey);
            return  DataResponse.builder()
                    .success(true)
                    .status(200)
                    .message(url)
                    .build();
        }
        catch (Exception e){
            e.printStackTrace();
            return DataResponse.builder()
                    .success(false)
                    .status(500)
                    .message("Internal error")
                    .build();
        }
    }

    // upload post's images
    @PostMapping("/post/upload-images")
    public ArrayResponse uploadPostImages(@RequestBody ArrayList<byte[]> imageBytesList) {
        try {
            List<String> imageKeys = this.fileService.uploadPostFiles(imageBytesList);
            return ArrayResponse.builder()
                    .success(true)
                    .status(200)
                    .data(imageKeys)
                    .build();
        } catch (Exception e) {
            return ArrayResponse.builder()
                    .success(false)
                    .status(500)
                    .message("Failed to upload images")
                    .build();
        }
    }


    // get post's images
    @GetMapping("/post/images")
    public ArrayResponse getPostImages(@RequestParam("keys") String keys){

        try{
            String[] parts = keys.split(",");
            List<String> urls = this.fileService.getPostImages(Arrays.asList(parts));
            return ArrayResponse.builder()
                    .success(true)
                    .status(200)
                    .data(urls)
                    .build();
        }
        catch (Exception e){
            e.printStackTrace();
            return ArrayResponse.builder()
                    .success(false)
                    .status(500)
                    .message("Internal error")
                    .build();
        }

    }

    // upload post's attachments
    @PostMapping("/post/upload-attachments")
    public AttachmentsResponse uploadPostAttachments(@RequestBody List<byte[]> files){
        try{
            List<String> fileKeys = this.fileService.uploadPostFiles(files);
            return AttachmentsResponse.builder()
                    .success(true)
                    .status(200)
                    .keys(fileKeys)
                    .build();
        }
        catch (Exception e){
            return AttachmentsResponse.builder()
                    .success(false)
                    .status(500)
                    .message("Internal error")
                    .build();
        }
    }

    // download post's attachments
    @GetMapping("/post/attachments")
    public AttachmentsResponse getPostAttachments(@RequestParam("keys") String keys) {

        try {
            String[] parts = keys.split(",");
            List<Attachment> attachments = this.fileService.getPostAttachments(Arrays.asList(parts));
            return AttachmentsResponse.builder()
                    .success(true)
                    .status(200)
                    .files(attachments)
                    .build();

        } catch (Exception e) {
            return AttachmentsResponse.builder()
                    .success(false)
                    .status(500)
                    .message("Internal error")
                    .build();
        }
    }

}
