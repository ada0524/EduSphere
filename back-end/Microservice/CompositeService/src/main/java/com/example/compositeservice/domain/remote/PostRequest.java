package com.example.compositeservice.domain.remote;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class PostRequest {
    private String title;
    private String content;
    private List<String> images;
    private List<String> attachments;
}
