package com.example.compositeservice.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class CompositeRequest {
    private String title;
    private String content;
    private List<MultipartFile> images;
    private List<MultipartFile> attachments;

}
