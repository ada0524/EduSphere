package com.example.compositeservice.service.remote;

import com.example.compositeservice.domain.remote.ArrayResponse;
import com.example.compositeservice.domain.remote.AttachmentsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("file-service")
public interface FileServiceClient {

//    @PostMapping("/user/upload-profile")
//    DataResponse uploadUserProfile(@RequestBody byte[] image);
//
//    @GetMapping("/user/profile")
//    DataResponse getUserProfile(@RequestParam("key") String imageKey);

    @PostMapping("file-service/post/upload-images")
    ArrayResponse uploadPostImages(@RequestBody List<byte[]> images);

    @GetMapping("file-service/post/images")
    ArrayResponse getPostImages(@RequestParam("keys") String keys);

    @PostMapping("file-service/post/upload-attachments")
    AttachmentsResponse uploadPostAttachments(@RequestBody List<byte[]> attachments);

    @GetMapping("file-service/post/attachments")
    AttachmentsResponse getPostAttachments(@RequestParam("keys") String keys);
}
