package com.example.postandreplyservice.service.remote;

import com.example.postandreplyservice.dto.remote.ArrayResponse;
import com.example.postandreplyservice.dto.remote.AttachmentsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient("file-service")
public interface RemoteFileService {

    @PostMapping("/post/upload-images")
    ArrayResponse uploadPostImages(@RequestBody List<MultipartFile> images);

    @GetMapping("/post/images")
    ArrayResponse getPostImages(@RequestParam("keys") String keys);

    @PostMapping("/post/upload-attachments")
    public AttachmentsResponse uploadPostAttachments(@RequestBody List<MultipartFile> files);

    @GetMapping("/post/attachments")
    public AttachmentsResponse getPostAttachments(@RequestParam("keys") String keys);
}
